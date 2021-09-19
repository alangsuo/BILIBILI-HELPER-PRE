#!/bin/bash
source /etc/profile 
source ~/.bashrc 
source ~/.zshrc #其他终端请自行引入环境变量
echo $PATH
java -jar /home/BILIBILI-HELPER.jar config.json >> /var/log/bilibili-help.log