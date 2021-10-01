#!/usr/bin/env bash
#new Env('BILIBILI-HELPER');

if ! [ -x "$(command -v java)" ]; then
	echo "开始安装Java运行环境........."
	apk update
	apk add openjdk8
fi
if [ ! -d "/ql/scripts/bilibili/" ]; then
	mkdir /ql/scripts/bilibili
fi
cd bilibili
if [ -f "/tmp/bili-helper.log" ]; then
	VERSION=$(grep "当前版本" "/tmp/bili-helper.log" | awk '{print $2}')
else
	VERSION="0"
fi
echo "当前版本:"$VERSION
latest=$(curl -s https://api.github.com/repos/JunzhouLiu/BILIBILI-HELPER-PRE/releases/latest)

latest_VERSION=$(echo $latest | jq '.tag_name' | sed 's/v\|"//g')
echo "最新版本:"$latest_VERSION
download_url=$(echo $latest | jq '.assets[0].browser_download_url' | sed 's/"//g')
download() {
	curl -L -o "./BILIBILI-HELPER.zip" "https://ghproxy.com/$download_url"
	mkdir ./tmp
	echo "正在解压文件......."
	unzip -o -d ./tmp/ BILIBILI-HELPER.zip
	cp -f ./tmp/BILIBILI-HELPER*.jar BILIBILI-HELPER.jar
	if [ ! -f "./config.json" ]; then
		echo "配置文件不存在。"
		cp -f ./tmp/config.json config.json
	fi
	echo "清除缓存........."
	rm -rf tmp
	rm -rf BILIBILI-HELPER.zip
	echo "更新完成"
}
function version_lt() { test "$(echo "$@" | tr " " "\n" | sort -rV | head -n 1)" != "$1"; }
if version_lt $VERSION $latest_VERSION; then
	echo "有新版本，开始更新"
	download
else
	echo "已经是最新版本，不需要更新！！！"
fi
if [ ! -f "/ql/scripts/bilibili/BILIBILI-HELPER.jar" ]; then
	echo "没找到BILIBILI-HELPER.jar，开始下载.........."
	download
fi
files=$(ls *.json)
for file_name in $files; do
	echo $file_name
	java -jar BILIBILI-HELPER.jar $file_name
done
