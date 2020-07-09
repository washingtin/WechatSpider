/**
 * 微信公众号爬虫, 抓取流程参考`README.MD`文档
 *
 * @author 最爱吃小鱼
 */

// 规则配置
var config = {
    host: 'http://127.0.0.1:9012', // 服务器地址配置
    crawlHistory: true, // 是否采集列表历史数据
    crawlArticle: true, // 是否采集文章数据
    crawlComment: true, // 是否采集评论数据
    crawlLikeReadNum: true, // 是否采集文章的阅读量及点赞量
    autoNextScroll: true, // 是否自动下拉采取数据
    autoNextPage: true, // 是否自动文章翻页
    autoPostData: true, // 是否提交数据到服务器
    m: 3000, // 自动下拉的时间间隔 m ~ n 秒之间
    n: 5000,
    jumpInterval: 10, // 文章页跳转的时间间隔
    saveContentType: 'text',// 微信文章保存内容的形式: html/text
    localImg: true // 公众号的图片返回本地图片
}

var url = require('url');
var http = require('http');
var querystring = require('querystring');
var cheerio = require('cheerio');
var rp = require('request-promise');
var fs = require("fs");
var img = fs.readFileSync(__dirname + "/black.png")
module.exports = {
    // 模块介绍
    summary: '微信公众号爬虫',
    // 发送请求拦截
    * beforeSendRequest(requestDetail) {
        if (!config.localImg) return null;
        // 将请求图片变成本地图片，加快文章显示
        if (/mmbiz\.qpic\.cn/i.test(requestDetail.url)) {
            const localResponse = {
                statusCode: 200,
                header: {'Content-Type': 'image/png'},
                body: img
            };
            return {
                response: localResponse
            };
        }
    },
    // 发送响应前处理
    * beforeSendResponse(requestDetail, responseDetail) {
        try {
            // 解析连接中的参数信息
            var link = requestDetail.url;

            // 历史页面第一页数据
            if (/mp\/profile_ext\?action=home/i.test(link)) {
                if (!config.crawlHistory) {
                    return null;
                }
                // 取得响应内容
                var serverResData = responseDetail.response.body.toString();
                // 取得公众号唯一标识biz
                var biz = getBizByLink(link);

                // 取得微信公众号历史数据的第一页数据，包含公众号详情及最新的文章信息
                var account = getAccount(biz, serverResData);
                // 数据上传到服务器
                serverPost(biz, account, '/spider/firstpage')

                // 判断是否自动下拉请求数据
                if (!config.autoNextScroll) {
                    return null;
                }
                // 根据返回的数据状态组装相应的自动滚动加载JS
                var autoNextScrollJS = getAutoNextScrollJS();

                // 修改返回的body内容，插入JS
                var newResponse = Object.assign({}, responseDetail.response);
                newResponse.body += autoNextScrollJS;
                return {
                    response: newResponse
                };
            }

            // 向下翻页的数据的AJAX请求处理
            if (/mp\/profile_ext\?action=getmsg/i.test(link)) {
                if (!config.crawlHistory) {
                    return null;
                }
                var biz = getBizByLink(link);
                var content = JSON.parse(responseDetail.response.body.toString());
                content = JSON.parse(content.general_msg_list);

                var articles = getArticles(biz, content.list);
                serverPost(biz, articles, '/spider/nextpage');
                return null;
            }

            // 文章页跳转
            if (/\/s\?__biz/.test(link) || /mp\/appmsg\/show/.test(link)) {
                if (!config.crawlArticle) {
                    return null;
                }
                var content = responseDetail.response.body.toString();
                var article = getArticle(link, content);
                let bb = async function () {
                    return await serverPost(article.biz, article, "/spider/updateArticleContent").then((nextLink) => {
                        if (nextLink == undefined || nextLink == null || nextLink == '') {
                            return null;
                        }
                        var autoNextPageMeta = getAutoNextPageMeta(nextLink);
                        console.log(nextLink);

                        // 修改返回的body内容，插入meta
                        var newResponse = Object.assign({}, responseDetail.response);
                        newResponse.body = content.replace('</title>', '</title>' + autoNextPageMeta);
                        return {
                            response: newResponse
                        };
                    });
                }
                return bb();
            }

            // 微信公众号的跟贴评论
            if (/\/mp\/appmsg_comment/.test(link)) {
                if (!config.crawlComment) {
                    return null;
                }
                var comments = getComments(link, responseDetail);
                if (comments.length > 0) {
                    serverPost(comments[0].biz, comments, '/spider/comment');
                }
                return null;
            }

            // 获取点赞量和阅读量
            if (link.indexOf('getappmsgext') > -1) {
                if (!config.crawlLikeReadNum) {
                    return null;
                }
                var article = getArticleOfReadNumAndLikeNum(link, responseDetail);
                serverPost(article.biz, article, '/spider/updateArticleNum');
                return null;
            }

            return null;
        } catch (e) {
            console.log("程序运行异常");
            console.log(e);
            throw e;
        }

    }
};


// 转义符换成普通字符
function escape2Html(str) {
    const arrEntities = {'lt': '<', 'gt': '>', 'nbsp': ' ', 'amp': '&', 'quot': '"'};
    return str.replace(/&(lt|gt|nbsp|amp|quot);/ig, function (all, t) {
        return arrEntities[t];
    });
}

/**
 * 取得文章的详细信息, 通过biz,mid聚合主键找到此文章，更新文章内容
 *
 * @param link
 * @param responseDetail
 * @returns {{biz: *, mid: *, idx: *, content: (*|jQuery|string)}}
 */
function getArticle(link, content) {
    var $ = cheerio.load(content, {decodeEntities: false});
    var identifier = querystring.parse(url.parse(link).query);
    var articleContent = config.saveContentType == 'html' ? $('#js_content').html() : $('#js_content').text();
    if (articleContent == '') {
        return null;
    }
    return {
        biz: identifier.__biz,
        mid: identifier.mid,
        idx: identifier.idx,
        content: articleContent
    }
}

/**
 * 取得文章的阅读量、点赞量等相关文章信息
 *
 * @param link
 * @param responseDetail
 * @returns {{biz: *, mid: *, idx: *, readNum: *, likeNum: *}}
 */
function getArticleOfReadNumAndLikeNum(link, responseDetail) {
    var identifier = querystring.parse(url.parse(link).query);
    var content = JSON.parse(responseDetail.response.body.toString());
    return {
        biz: identifier.__biz,
        mid: identifier.mid,
        idx: identifier.idx,
        readNum: content.appmsgstat.read_num,
        likeNum: content.appmsgstat.like_num
    }
}

/**
 * 从URL中解析出biz
 * @param link
 * @returns {biz}
 */
function getBizByLink(link) {
    var identifier = querystring.parse(url.parse(link).query);
    return identifier.__biz;
}

/**
 * 取得微信公众号及最新的文章信息
 * @param biz
 * @param serverResData
 * @returns {{}}
 */
function getAccount(biz, serverResData) {
    var account = {};
    // 解析公众号的数据
    account.nickname = /var nickname = "(.+?)"/.exec(serverResData)[1];
    account.headimg = /var headimg = "(.+?)"/.exec(serverResData)[1];
    account.biz = biz;
    account.crawlTime = new Date().getTime();

    // 解析文章列表数据
    var msgList = /var msgList = '(.+)';\n/.exec(serverResData)[1];
    msgList = JSON.parse(escape2Html(msgList).replace(/\\\//g, '/'));
    msgList = msgList.list;
    account.articles = getArticles(biz, msgList);
    return account;
}

/**
 * 解析封装取得自己想要文章信息
 * @param biz
 * @param content
 * @returns {Array}
 */
function getArticles(biz, content) {
    var articles = [];
    for (var i = 0, len = content.length; i < len; i++) {
        var post = content[i];
        var cmi = post.comm_msg_info;
        // 只采取图文消息的数据，目前所知type=3就只有一张图片，其它类型未知
        if (cmi.type != 49) continue;
        var amei = post.app_msg_ext_info;
        var obj = getMidAndIdx(amei.content_url);

        articles.push({
            biz: biz,
            mid: obj.mid,
            title: amei.title,
            digest: amei.digest,
            contentUrl: amei.content_url,
            sourceUrl: amei.source_url,
            author: amei.author,
            cover: amei.cover,
            copyrightStat: amei.copyright_stat,
            datetime: cmi.datetime,
            idx: obj.idx
        });
    }
    return articles;
}

/**
 * 解析封装取得文章的评论信息
 * @param link
 * @param responseDetail
 * @returns {Array}
 */
function getComments(link, responseDetail) {
    var identifier = querystring.parse(url.parse(link).query);
    var comments = [];
    var content = JSON.parse(responseDetail.response.body.toString());
    var electedComment = content.elected_comment;
    if (electedComment && electedComment.length) {
        for (var i = 0, len = electedComment.length; i < len; i++) {
            comments.push({
                biz: identifier.__biz,
                mid: identifier.appmsgid,
                contentId: electedComment[i].content_id,
                nickName: electedComment[i].nick_name,
                logoUrl: electedComment[i].logo_url,
                content: electedComment[i].content,
                createTime: electedComment[i].create_time,
                likeNum: electedComment[i].like_num
            });
        }
    }
    return comments;
}

/**
 * 从连接取得mid及idx
 * @param link
 * @returns {{mid: *, idx: *}}
 */
function getMidAndIdx(link) {
    var identifier = querystring.parse(url.parse(link.replace(/amp;/g, '')).query);
    return {
        mid: identifier.mid,
        idx: identifier.idx
    }
}

/**
 * 向服务上传抓取到的数据
 * @param data 数据
 * @param path 请求路径
 */
function serverPost(biz, data, path) {
    if (!config.autoPostData) {
        return Promise.resolve().then(function () {
            return null;
        });
    }
    var options = {
        method: 'POST',
        uri: config.host + path,
        form: {
            biz: biz,
            content: JSON.stringify(data)
        },
        json: true
    };
    return rp(options).then(function (parsedBody) {
        if (parsedBody.code == 1) {
            console.log('--' + path + '------------------------');
            //console.log(data);
            return parsedBody.data;
        } else {
            console.log('请求失败, 失败信息=' + parsedBody.message);
            return null;
        }
    });
}

/**
 * 组装自动向下滚动翻页的JS
 *
 * @returns {string}
 */
function getAutoNextScrollJS() {
    var nextJS = '';
    nextJS += '<script type="text/javascript">';
    nextJS += '    var end = document.createElement("p");';
    nextJS += '    document.body.appendChild(end);';
    nextJS += '    (function scrollDown(){';
    nextJS += '        end.scrollIntoView();';
    nextJS += '        var loadMore = document.getElementsByClassName("loadmore with_line")[0];';
    nextJS += '        if (loadMore.style.display) {';
    nextJS += '            setTimeout(scrollDown,Math.floor(Math.random()*(' + config.n + '-' + config.m + ')+' + config.m + '));';
    nextJS += '        } ';
    nextJS += '    })();';
    nextJS += '<\/script>';
    return nextJS;
}

/**
 * 自动跳转到下一文章页的meta
 *
 * @param nextLink
 * @returns {string}
 */
function getAutoNextPageMeta(nextLink) {
    return '<meta http-equiv="refresh" content="' + config.jumpInterval + ';url=' + nextLink + '" />';
}