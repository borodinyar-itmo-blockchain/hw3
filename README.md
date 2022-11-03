#  HW3


### Конфигурация

В файле `config.properties` необходимо написать адрес ноды и адреса оракулов.

```
node_url = <YOUR NODE WEBSOCKET ADRESS> 

tokens = eth_usd, link_eth, usdt_eth
eth_usd = 0x37bC7498f4FF12C19678ee8fE19d713b87F6a9e6
link_eth = 0xbba12740DE905707251525477bAD74985DeC46D2
usdt_eth = 0x7De0d6fce0C128395C488cb4Df667cdbfb35d7DE 
```

### Cборка

Для сборки нужно запустить команду

```
mvn clean install
```

### Запуск

```
mvn exec:java -Dexec.mainClass="com.borodinyar.java_monitoring.Main"
```

### Пример лога

```
New block: ts=1667501687, number=15891408, hash=0x92b9f4c9d1c644f8935c837fb40df70f2452f1d889d64ee9cefd46bf224de8db 
New block: ts=1667501699, number=15891409, hash=0x04396efd6adc06145c1d8b7f776b92cf23a14958b47f4b22951380d6108a9b0b 
New block: ts=1667501711, number=15891410, hash=0x975ae73976f78f0b88fe30b0d84391c5417fbdf469fed7b5971e102117f54839 
Contract eth_usd is updated: ts=1667501723, current=154554182432, roundId=36212 
New block: ts=1667501723, number=15891411, hash=0xbe1948a72807ff5cbc02d4c35f53b44158552c5847dc64f5a94410be8475f94c 
New block: ts=1667501735, number=15891412, hash=0xd30a9219682d805400bb67853596e17e761f351991ccadb1d75ba59212bfd44e 
```

