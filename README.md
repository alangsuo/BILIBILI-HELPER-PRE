<div align="center">
<h1 align="center">
BILIBILI-HELPER
</h1>

[![GitHub stars](https://img.shields.io/github/stars/JunzhouLiu/BILIBILI-HELPER-PRE?style=flat-square)](https://github.com/JunzhouLiu/BILIBILI-HELPER-PRE/stargazers)
[![GitHub forks](https://img.shields.io/github/forks/JunzhouLiu/BILIBILI-HELPER-PRE?style=flat-square)](https://github.com/JunzhouLiu/BILIBILI-HELPER-PRE/network)
[![GitHub issues](https://img.shields.io/github/issues/JunzhouLiu/BILIBILI-HELPER-PRE?style=flat-square)](https://github.com/JunzhouLiu/BILIBILI-HELPER-PRE/issues)
[![GitHub license](https://img.shields.io/github/license/JunzhouLiu/BILIBILI-HELPER-PRE?style=flat-square)](https://github.com/JunzhouLiu/BILIBILI-HELPER-PRE/blob/main/LICENSE)
[![GitHub All Releases](https://img.shields.io/github/downloads/JunzhouLiu/BILIBILI-HELPER-PRE/total?style=flat-square)](https://github.com/JunzhouLiu/BILIBILI-HELPER-PRE/releases)
[![Docker Pulls](https://img.shields.io/docker/pulls/superng6/bilibili-helper?style=flat-square)](https://hub.docker.com/r/superng6/bilibili-helper)
[![GitHub release (latest SemVer)](https://img.shields.io/github/v/release/JunzhouLiu/BILIBILI-HELPER-PRE?style=flat-square)](https://github.com/JunzhouLiu/BILIBILI-HELPER-PRE/releases)
[![Hits](https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2FJunzhouLiu%2FBILIBILI-HELPER-PRE&count_bg=%2379C83D&title_bg=%23555555&icon=&icon_color=%23E7E7E7&title=hits&edge_flat=false)](https://hits.seeyoufarm.com)
[![FOSSA Status](https://app.fossa.com/api/projects/git%2Bgithub.com%2FJunzhouLiu%2FBILIBILI-HELPER-PRE.svg?type=small)](https://app.fossa.com/projects/git%2Bgithub.com%2FJunzhouLiu%2FBILIBILI-HELPER-PRE?ref=badge_small)

</div>

## 工具简介

这是一个利用 Linux Crontab ,云函数， Docker 等方式实现哔哩哔哩（Bilibili）每日任务投币，点赞，分享视频，直播签到，银瓜子兑换硬币，漫画每日签到，简单配置即可每日轻松获取 65 经验值，快来和我一起成为 Lv6
吧\~\~\~\~

**如果觉得好用，顺手点个 Star 吧 ❤**

**仓库地址：[JunzhouLiu/BILIBILI-HELPER](https://github.com/JunzhouLiu/BILIBILI-HELPER-PRE)**

**B 站赛事预测助手已发布，每天自动参与 KPL，LPL 赛事预测，赚取硬币。**

**仓库地址：[JunzhouLiu/bilibili-match-prediction](https://github.com/JunzhouLiu/bilibili-match-prediction)**

**请不要滥用相关 API，让我们一起爱护 B 站 ❤**

## 功能列表

- [x] 通过docker或者云函数执行定时任务。_【运行时间可自定义】_
- [x] 哔哩哔哩漫画每日自动签到，自动阅读 1 章节 。
- [x] 每日自动从热门视频中随机观看 1 个视频，分享一个视频。
- [x] 每日从热门视频中选取 5 个进行智能投币 _【如果投币不能获得经验了，则不会投币】_
- [x] 投币支持下次一定啦，可自定义每日投币数量。_【如果检测到你已经投过币了，则不会投币】_
- [x] 大会员月底使用快到期的 B 币券，给自己充电，一点也不会浪费哦，默认开启。_【已支持给指定 UP 充电】_
- [x] 大会员月初 1 号自动领取每月 5 张 B 币券和福利。
- [x] 每日哔哩哔哩直播自动签到，领取签到奖励。_【直播你可以不看，但是奖励咱们一定要领】_
- [x] 投币策略更新可配置投币喜好。_【可配置优先给关注的 up 投币】_
- [x] 自动送出即将过期的礼物。 _【默认开启，未更新到新版本的用户默认关闭】_
- [x] 支持推送执行结果到微信，钉钉，飞书等。
- [x] 支持赛事预测。_【支持反向预测】_

[点击快速开始使用](#使用说明)

[点击快速查看自定义功能配置](#自定义功能配置)

# 目录

- [目录](#目录)
  - [使用说明](#使用说明)
    - [获取执行所需的cookies](#获取执行所需的cookies)
    - [一、快速使用](#一快速使用)
    - [二、使用 Docker](#二使用-docker)
    - [三、使用 腾讯云函数](#三使用-腾讯云函数)
    - [自定义功能配置](#自定义功能配置)
      - [配置文件参数](#配置文件参数)
  - [免责声明](#免责声明)
  - [API 参考列表](#api-参考列表)
  - [基于本项目的衍生项目](#基于本项目的衍生项目)
  - [致谢](#致谢)
  - [讨论群](#讨论群)
  - [License](#license)
  - [Stargazers over time](#stargazers-over-time)

## 使用说明

### 获取执行所需的cookies

1. 浏览器打开并登录 [bilibili][1] 网站
2. 按 F12 打开 「开发者工具」 打开 网络/NetWork -> 找到并点击nav请求
3. 下拉请求详情，复制完整的cookie和UA备用。

ps:cookie和ua只需要选中，右键复制值即可。

![准备cookie](docs//IMG/ck.png)

[1]:https://www.bilibili.com/

### 一、快速使用

1. 点击 [BILIBILI-HELPER/release][3]，下载已发布的版本，解压后压缩包内应该包含一个jar包和一份`config.json`文件。
2. 将获取的到的cookie字符串和ua字符串填写到config.json中的 `biliCookies`和`userAgent`字段值中。

[3]: https://github.com/JunzhouLiu/BILIBILI-HELPER-PRE/releases/latest

3. 在当前目录执行 `java -jar BILIBILI-HELPER.jar` 即可开始执行任务（需要保证`config.json`文件存在并且配置好了ck等）。

ps ：需要本地有java8执行环境。

### 二、使用 Docker

请自行参阅 [基于本项目的衍生项目](#基于本项目的衍生项目)。

- **基于本项目的 docker 封装项目：[SuperNG6/docker-bilibili-helper](https://github.com/SuperNG6/docker-bilibili-helper)**

- **基于本项目的 docker 镜像：[superng6/bilibili-helper](https://hub.docker.com/r/superng6/bilibili-helper)**

### 三、使用 腾讯云函数

请参考[腾讯云函数部署](https://github.com/JunzhouLiu/BILIBILI-HELPER-PRE/blob/main/docs/scf.md)

### 自定义功能配置

配置文件示例：

```json
{
  "biliVerify": {
    "biliCookies": "你的bilibili cookies"
  },
  "taskConfig": {
    "skipDailyTask": false,
    "matchGame": false,
    "showHandModel": false,
    "predictNumberOfCoins": 1,
    "minimumNumberOfCoins": 100,
    "taskIntervalTime": 20,
    "numberOfCoins": 5,
    "reserveCoins": 10,
    "selectLike": 0,
    "monthEndAutoCharge": true,
    "giveGift": true,
    "silver2Coin": true,
    "upLive": "0",
    "chargeForLove": "14602398",
    "chargeDay": 8,
    "devicePlatform": "ios",
    "coinAddPriority": 1,
    "userAgent": "你的默认UA"
  },
  "pushConfig": {
    "SC_KEY": "",
    "SCT_KEY": "",
    "TG_BOT_TOKEN": "",
    "TG_USER_ID": "",
    "DING_TALK_URL": "",
    "DING_TALK_SECRET": "",
    "PUSH_PLUS_TOKEN": "",
    "WE_COM_GROUP_TOKEN": "",
    "WE_COM_APP_TOKEN_CONF": "",
    "PROXY_HTTP_HOST": "",
    "PROXY_SOCKET_HOST": "",
    "PROXY_PORT": 0
  }
}

```

**如果不知道自己的浏览器UA之类数据，以及看不懂每个值的含义，可以采用**[配置生成网页](https://utils.misec.top/index)**生成自己的配置（暂未支持v2.0.0版本）**

#### 配置文件参数

 **biliVerify**

| Key(字段)   | Value(值) | 说明                                      |
| ----------- | --------- | ----------------------------------------- |
| biliCookies | str       | bilibili 的cookie，获取方式请查看使用说明 |

 **taskConfig**

   | Key(字段)            | Value(值)            | 说明                                                                   |
   | -------------------- | -------------------- | ---------------------------------------------------------------------- |
   | matchGame            | [false,true]         | 是否开启赛事预测。                                                     |
   | showHandModel        | [false,true]         | `true` ：压赔率高的，`false`：压赔率低的。                                   |
   | predictNumberOfCoins | [1,10]               | 单次预测的硬币数量,默认为`1`。                                             |
   | minimumNumberOfCoins | [1,无穷大]           | 预留的硬币数，低于此数量不执行赛事预测。                               |
   | taskIntervalTime     | [1,无穷大]           | 任务之间的执行间隔,默认`10`秒,云函数用户不建议调整的太长，注意免费时长。 |
   | numberOfCoins        | [0,5]                | 每日投币数量,默认 `5` ,为 `0` 时则不投币。                                   |
   | reserveCoins         | [0,4000]             | 预留的硬币数，当硬币余额小于这个值时，不会进行投币任务，默认值为 `50`。    |
   | selectLike           | [0,1]                | 投币时是否点赞，默认 `0`, `0`：否 `1`：是。                                    |
   | monthEndAutoCharge   | [false,true]         | 年度大会员月底是否用 B 币券自动充电，默认 `true`。                     |
   | chargeDay            | [1，28]              | 充电日期，默认为每月`28`号。                                                         |
   | chargeForLove        | [充电对象的 uid]      | 给指定 up 主充电，可填写充电对象的 UID ,默认给作者充电。                   |
   | giveGift             | [false,true]         | 直播送出即将过期的礼物，默认开启，如需关闭请改为 `false`。                 |
   | upLive               | [0,送礼 up 主的 uid] | 直播送出即将过期的礼物，可填写指定 up 主的 UID ，为 `0` 时则随随机选取一个 up 主。      |
   | silver2Coin          | [false,true]         | 银瓜子兑换硬币，默认开启，如需关闭请改为 `false`。
   | devicePlatform       | [ios,android]        | 手机端漫画签到时的平台，建议选择你设备的平台 ，默认 `ios`。              |
   | coinAddPriority      | [0,1]                | `0`：优先给热榜视频投币，`1`：优先给关注的 up 投币。                         |
   | userAgent            | 浏览器 UA            | 你的浏览器的UA。                                                           |
   | skipDailyTask        | [false,true]         | 是否跳过每日任务，默认`true`,如果关闭跳过每日任务，请改为`false`。       |

- **tips:从1.0.0版本开始，随机视频投币有一定的概率会将硬币投给本项目的开发者。**
- **默认配置文件是给开发者充电，给自己充电或者给其他up充电，请改为对应的uid**
- **userAgent建议使用你自己真实常用浏览器UA，如果不知道自己的UA请到[配置生成页面查看你的UA](https://utils.misec.top/index)**


 **pushConfig**

| Key(字段)         | Value(值) | 说明                                            |
| ----------------- | --------- | ----------------------------------------------- |
| SC_KEY            | str       | Server酱老版本key，SCU开头的                    |
| SCT_KEY           | str       | Server酱Turbo版本key，SCT开头的                 |
| TG_USE_CUSTOM_URL |[false,true]| TGbotAPI反代开启选项，若开启，则`TG_BOT_TOKEN`填写为完整反代url，e.g.`https://api.telegram-proxy.org/bot?token=xxx`                 |
| TG_BOT_TOKEN      | str       | TG推送bot_token,若`TG_USE_CUSTOM_URL`开启，则填写为完整反代url，e.g.`https://api.telegram-proxy.org/bot?token=xxx`                                     |
| TG_USER_ID        | str       | TG推送的用户/群组/频道ID                                        |
| PUSH_PLUS_TOKEN   | str       | push plus++推送的`token`                                     |
| DING_TALK_URL     | str       | 钉钉推送的完整URL,e.g.`https://oapi.dingtalk.com/robot/send?access_token=xxx`                                            |
| DING_TALK_SECRET  | str       | 钉钉推送的密钥                                      |
| PROXY_HTTP_HOST   | str       | 推送高级协议(HTTP/FTP)代理的域名/IP,e.g.`127.0.0.1` |
| PROXY_SOCKET_HOST | str       | 推送SOCKS(V4/V5)代理的域名/IP,e.g.`127.0.0.1`        |
| PROXY_PORT        | int       | 推送代理的端口                        |
- **tips:`PROXY_HTTP_HOST`和`PROXY_SOCKET_HOST`仅需填写一个。**
- **tips:钉钉推送密钥可不填，仅用关键词验证。**


## 免责声明

1. 本项目的初衷是为了辅助大家更快的升级到lv6，在使用的过程中不会记录或上传任何数据，执行过程中的数据均存在你自己的设备上。
2. 开源的目的单纯是技术分享，所有代码细节都公开，本项目不会增加类似于自动转发抽奖，秒杀，下载版权受限视频等侵犯 UP 主/B 站权益的功能。
3. 请不要把自己的敏感信息（账号，cookies等）提供给他人。（**网络安全教育普及任重而道远**）
4. 本工具源码仅在[JunzhouLiu/BILIBILI-HELPER-PRE](https://github.com/JunzhouLiu/BILIBILI-HELPER-PRE)开源。
5. 本项目遵守[MIT License](https://github.com/JunzhouLiu/BILIBILI-HELPER-PRE/blob/main/LICENSE)，因使用造成的任何损失，纠纷等，和开发者无关，请各位知悉。

## API 参考列表

- [SocialSisterYi/bilibili-API-collect](https://github.com/SocialSisterYi/bilibili-API-collect)
- [happy888888/BiliExp](https://github.com/happy888888/BiliExp)

## 基于本项目的衍生项目

- **基于本项目的 docker 封装项目：[SuperNG6/docker-bilibili-helper](https://github.com/SuperNG6/docker-bilibili-helper)**

- **基于本项目的 docker 镜像：[superng6/bilibili-helper](https://hub.docker.com/r/superng6/bilibili-helper)**

- **基于本项目的 k8s 项目：[yangyang0507/k8s-bilibili-helper](https://github.com/yangyang0507/k8s-bilibili-helper)**

## 致谢

感谢 JetBrains 对本项目的支持。

[![JetBrains](docs/IMG/jetbrains.svg)](https://www.jetbrains.com/?from=BILIBILI-HELPER)

## 讨论群


qq 群二维码

![qq群二维码](docs/IMG/qqgroup.png)

[也可点击此处一键加群](https://qm.qq.com/cgi-bin/qm/qr?k=m_M1Fydi3MvrVAEM0Sp6hDfZF4N2SpXU&jump_from=webapi)


## License

[![FOSSA Status](https://app.fossa.com/api/projects/git%2Bgithub.com%2FJunzhouLiu%2FBILIBILI-HELPER-PRE.svg?type=large)](https://app.fossa.com/projects/git%2Bgithub.com%2FJunzhouLiu%2FBILIBILI-HELPER-PRE?ref=badge_large)

## Stargazers over time

[![Stargazers over time](https://starchart.cc/JunzhouLiu/BILIBILI-HELPER-PRE.svg)](https://starchart.cc/JunzhouLiu/BILIBILI-HELPER-PRE)
