# Zero

### jar构建
`./gradlew build docker `

### db镜像构建
`docker run -d -p 3306:3306 --name=mysql-db --env="MYSQL_ROOT_PASSWORD=root" --env="MYSQL_PASSWORD=root" --env="MYSQL_DATABASE=ZeroDB" mysql`

docker run --name mysql --env="MYSQL_ROOT_PASSWORD=root" --env="MYSQL_PASSWORD=root" --env="MYSQL_DATABASE=ZeroDB" -p 127.0.0.1:3306:3306 -d mysql:8

docker exec -it mysql mysql -u root -p
ALTER USER 'root'@'%' IDENTIFIED WITH mysql_native_password BY 'root';
FLUSH PRIVILEGES;


### 运行
`  docker run -p 8080:8080  --link docker-mysql:mysql -t mijack/gs-spring-boot-docker`

## todo
[ ] 支持graph ql查询 
[ ] 支持docker集成测试
[ ] 运行环境docker化
[ ] 采用六边形架构