# Zero

### jar构建
`./gradlew build docker `

### db镜像构建
`docker run -d -p 6033:3306 --name=docker-mysql --env="MYSQL_ROOT_PASSWORD=root" --env="MYSQL_PASSWORD=root" --env="MYSQL_DATABASE=book_manager" mysql`

### 运行
`  docker run -p 8080:8080  --link docker-mysql:mysql -t mijack/gs-spring-boot-docker`
