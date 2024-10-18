FROM openjdk:17

RUN mkdir -p /java_dumps

COPY build/libs/*.jar market-api.jar

CMD java \
        -Xms1024m \
        -Xmx1024m \
        -XX:+HeapDumpOnOutOfMemoryError \
        -XX:+ExitOnOutOfMemoryError \
        -XX:HeapDumpPath=/java_dumps/market-api-prod$(date +-%Y-%m-%d-%H%M%S).hprof \
        -Dfile.encoding=UTF-8 \
        -DlogAppender=STDOUT \
        -Dspring.profiles.active=prod \
        -Duser.timezone=Asia/Seoul \
        -jar market-api.jar