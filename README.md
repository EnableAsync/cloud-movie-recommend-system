# 基于 Spark 的微服务推荐系统
## 组件选择
|  功能   | 选择   |
| :----:  | :----: |
| 服务注册 | Nacos  |
| 服务发现 | Nacos  |
| 服务降级 | Sentinel |
| 服务熔断 | Sentinel |
| 服务调用 | Feign |
| 服务网关 | Spring Cloud Gateway |
| 数据库   | MongoDB |
| 缓存数据库 | Redis |
| 消息队列 | Kafka |
| 全文索引 | ElasticSearch |
| 离线推荐 | Spark MLlib |
| 离线统计 | Spark SQL |
| 流式计算 | Spark Streaming |

## 微服务架构
![微服务架构图](docs/微服务架构图.svg)

## 推荐系统架构
![推荐系统架构图](docs/架构图.svg)

## 注册
![注册](docs/注册.jpg)

## 登录
![登录](docs/登录.jpg)

## 冷启动
![冷启动](docs/冷启动.jpg)

## 近期热门
![近期热门](docs/近期热门.jpg)

## 历史热门
![历史热门](docs/历史热门.jpg)

## 电影详情页
![电影详情页](docs/电影详情页.jpg)

## 相似电影
![相似电影](docs/相似电影.jpg)

## 排行榜
![排行榜](docs/排行榜.jpg)

## 分类排行榜
![分类排行榜](docs/分类排行榜.jpg)

## 猜你喜欢
![猜你喜欢](docs/猜你喜欢.jpg)

## 实时推荐
![实时推荐](docs/实时推荐.jpg)

## 前端
使用了 Vue + Vuetify，详情见
[推荐系统前端](https://github.com/EnableAsync/recommender-frontend)
