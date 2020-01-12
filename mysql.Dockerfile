FROM mysql:8
ENV MYSQL_ROOT_PASSWORD=root
ENV MYSQL_PASSWORD=root
ENV MYSQL_DATABASE=ZeroDB
EXPOSE 127.0.0.1:3306:3306
#ENTRYPOINT ["java","-cp","app:app/lib/*","com.mijack.zero.framework.ZeroApplication"]
#docker run --name mysql --env="MYSQL_ROOT_PASSWORD=root" --env="MYSQL_PASSWORD=root" --env="MYSQL_DATABASE=ZeroDB" -p 127.0.0.1:3306:3306 -d mysql:8

