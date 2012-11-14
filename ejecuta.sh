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



echo -n "Generando stub del servidor"
rmic servidor.TwitterInitImpl
check

echo -n "Generando el stub del cliente"
rmic cliente.Cliente
check

echo -n "Lanzando servidor";
java -Djava.rmi.server.codebase=file:///home/xafilox/workspace/ProyectoTwitter/bin/ -classpath ../libs/mysql-connector-java-5.1.22-bin.jar:../libs/javaQuery\ 7.0.jar:../libs/jfxrt.jar:./ servidor.Servidor &
PIDSERV="$!";
check

echo "Lanzando cliente";
sleep 1;
java -Djava.rmi.server.codebaseile:///home/xafilox/workspace/ProyectoTwitter/bin/ -classpath ../libs/mysql-connector-java-5.1.22-bin.jar:../libs/javaQuery\ 7.0.jar:../libs/jfxrt.jar:./ cliente.TwitterClient
PIDCLI="$!";
check


echo -n "Finalizando servidor";
kill -9 $PIDSERV;
check 

