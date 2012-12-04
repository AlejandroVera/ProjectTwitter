#! /bin/bash

function check {
	if [ $? -eq '0' ]; then
                echo " [OK]";
        else
                echo " [ERROR]";
                exit 1;
        fi;
}
cd ./bin
echo -n "Lanzando rmiregistry"
if [ `ps aux | grep rmiregistry | wc -l` -eq '2' ]; then 
	echo " [ALREADY]" 
else 
	rmiregistry &
	check;
fi

direc=$(pwd);

echo -n "Generando stub del servidor"
rmic servidor.TwitterInitImpl
check

echo -n "Generando el stub del cliente"
rmic cliente.ClientCallbackListener
check

echo -n "Lanzando servidor";
java -Djava.rmi.server.codebase=file:///${direc}/servidor/ -classpath ../libs/*:./ servidor.TwitterInitImpl &
PIDSERV="$!";
check

echo "Lanzando cliente";
sleep 1;
java -Djava.rmi.server.codebaseile:file///${direc}/cliente/ -classpath ../libs/*:./ cliente.TwitterClient
PIDCLI="$!";
check


echo -n "Finalizando servidor";
kill -9 $PIDSERV;
check 

