#!/bin/bash

#
# COPYRIGHT (C) 2022 Art AUTHORS(fxzcloud@gmail.com). ALL RIGHTS RESERVED.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

current_path=`pwd`
case "`uname`" in
    Linux)
                bin_abs_path=$(readlink -f $(dirname $0))
                ;;
        *)
                bin_abs_path=`cd $(dirname $0); pwd`
                ;;
esac
base=${bin_abs_path}/..
export LANG=en_US.UTF-8
export BASE=$base

if [ -f $base/bin/adapter.pid ] ; then
        echo "found adapter.pid , Please run stop.sh first ,then startup.sh" 2>&2
    exit 1
fi

if [ ! -d $base/logs ] ; then
        mkdir -p $base/logs
fi

## set java path
if [ -z "$JAVA" ] ; then
  JAVA=$(which java)
fi

ALIBABA_JAVA="/usr/alibaba/java/bin/java"
TAOBAO_JAVA="/opt/taobao/java/bin/java"
if [ -z "$JAVA" ]; then
  if [ -f $ALIBABA_JAVA ] ; then
        JAVA=$ALIBABA_JAVA
  elif [ -f $TAOBAO_JAVA ] ; then
        JAVA=$TAOBAO_JAVA
  else
        echo "Cannot find a Java JDK. Please set either set JAVA or put java (>=1.5) in your PATH." 2>&2
    exit 1
  fi
fi

case "$#"
in
0 )
  ;;
2 )
  if [ "$1" = "debug" ]; then
    DEBUG_PORT=$2
    DEBUG_SUSPEND="n"
    JAVA_DEBUG_OPT="-Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,address=$DEBUG_PORT,server=y,suspend=$DEBUG_SUSPEND"
  fi
  ;;
* )
  echo "THE PARAMETERS MUST BE TWO OR LESS.PLEASE CHECK AGAIN."
  exit;;
esac

str=`file -L $JAVA | grep 64-bit`
if [ -n "$str" ]; then
#        JAVA_OPTS="-server -Xms2048m -Xmx3072m"
        JAVA_OPTS="-server -Xms500m -Xmx500m"
else
#        JAVA_OPTS="-server -Xms1024m -Xmx1024m"
        JAVA_OPTS="-server -Xms500m -Xmx500m"
fi

JAVA_OPTS="$JAVA_OPTS -XX:+UseG1GC -XX:MaxGCPauseMillis=250 -XX:+UseGCOverheadLimit -XX:+ExplicitGCInvokesConcurrent -XX:+PrintAdaptiveSizePolicy -XX:+PrintTenuringDistribution"
JAVA_OPTS=" $JAVA_OPTS -Djava.awt.headless=true -Djava.net.preferIPv4Stack=true -Dfile.encoding=UTF-8"
CANAL_OPTS="-DappName=canal-admin"

for i in $base/lib/*;
    do CLASSPATH=$i:"$CLASSPATH";
done

CLASSPATH="$base/conf:$CLASSPATH";

echo "cd to $bin_abs_path for workaround relative path"
cd $bin_abs_path

echo CLASSPATH :$CLASSPATH
$JAVA $JAVA_OPTS $JAVA_DEBUG_OPT $CANAL_OPTS -classpath .:$CLASSPATH com.alibaba.otter.canal.admin.CanalAdminApplication 1>>/dev/null 2>&1 &
echo $! > $base/bin/admin.pid

echo "cd to $current_path for continue"
