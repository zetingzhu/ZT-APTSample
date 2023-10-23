# 无痕埋点插件

#### 介绍
**所谓的”无痕埋点”，就是无差别的记录用户在产品中的行为（全量数据），需要分析用户数据时，针对全量数据进行筛选**

相比于代码手动埋点，无痕埋点解决了如下问题：

- 通过代码手动埋点比较原始，出错概率较高。
- 埋点链路较长，出现错埋漏埋需要重新发布
- 埋点数据准确性无法校验



#### 架构分析
原来的手动埋点的方式为：产品或运营给出埋点列表，开发人员根据列表将埋点编入项目中，然后通过APP上报给服务端后，产品或运营根据服务端根据统计结果进行用户行为分析,相关架构图如下：

![手动埋点](https://img-blog.csdnimg.cn/9e31ab1780d44afe8f7b1ea85a4d6e87.png?x-oss-process=image/watermark,type_d3F5LXplbmhlaQ,shadow_50,text_Q1NETiBA55av5Lq66Zmi55qE6Zmi6ZW_5aSn5Lq6,size_20,color_FFFFFF,t_70,g_se,x_16)



无痕埋点则是应用为每一个控件生成唯一标识，在运行项目时，插件会通过插桩的形式为每一个控件添加点击监听，在获取到用户的点击事件后（如果控件未设置点击事件则该点击无效，目前在插件中做了过滤，只上报添加了点击的事件的控件点击，如果想实现全量数据上报可放开该限制）。



无痕埋点的架构如下：





控件的ID都是不同的，如何唯一标识传入的控件ID呢？有几种方法

#### Installation

1.  xxxx
2.  xxxx
3.  xxxx

#### Instructions

1.  xxxx
2.  xxxx
3.  xxxx

#### Contribution

1.  Fork the repository
2.  Create Feat_xxx branch
3.  Commit your code
4.  Create Pull Request


#### Gitee Feature

1.  You can use Readme\_XXX.md to support different languages, such as Readme\_en.md, Readme\_zh.md
2.  Gitee blog [blog.gitee.com](https://blog.gitee.com)
3.  Explore open source project [https://gitee.com/explore](https://gitee.com/explore)
4.  The most valuable open source project [GVP](https://gitee.com/gvp)
5.  The manual of Gitee [https://gitee.com/help](https://gitee.com/help)
6.  The most popular members  [https://gitee.com/gitee-stars/](https://gitee.com/gitee-stars/)
