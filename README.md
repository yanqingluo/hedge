# 研究股票对冲算法

##项目环境说明
java: 请使用jdk1.8,并无其他目的,电脑上装的就是jdk8,顺手就用了  
maven:参考配置[config/maven/setting.xml](https://github.com/harley-dog/hedge/blob/master/config/maven/settings.xml)  
主要是要连接oschina的库


##几个主要的类说明
com.diorsunion.hedge.bo.Win 用随机数随机模拟<br>
com.diorsunion.hedge.bo.test.RealWinTest 用真实数据进行模拟<br>
com.diorsunion.hedge.datainit.StockPriceDataInitRepositoryTest 从雪球上抓取股票数据到本地数据库<br>
com.diorsunion.hedge.algo 算法包,要实现的算法都写在这里<br>
