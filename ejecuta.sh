#! /bin/bash

function check {
	if [ $? -eq '0' ]; then
                echo " [OK]";
        else
                echo " [ERROR]";
                exit 1;
        fi;
}

echo -n "Lanzando rmiregistry"
if [ `ps aux | grep rmiregistry | wc -l` -eq '2' ]; then 
	echo " [ALREADY]" 
else 
	rmiregistry &
	check;
fi

cd ./bin

echo -n "Generando stub del servidor"
rmic ConexionImpl
check

echo -n "Generando el stub del cliente"
rmic Cliente
check

echo -n "Lanzando servidor";
java -Djava.rmi.server.codebase=file:///home/xafilox/workspace/ProyectoTwitter/bin/ Servidor &
PIDSERV="$!";
check

echo "Lanzando cliente";
sleep 1;
java -Djava.rmi.server.codebaseile:///home/xafilox/workspace/ProyectoTwitter/bin/ Cliente
PIDCLI="$!";
check


echo -n "Fnalizando servidor";
kill -9 $PIDSERV;
check 

