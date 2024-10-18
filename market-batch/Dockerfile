FROM openjdk:17

RUN mkdir -p /java_dumps

COPY build/libs/*.jar market-batch.jar

CMD java \
        -Xms1024m \
        -Xmx1024m \
        -XX:+HeapDumpOnOutOfMemoryError \
        -XX:+ExitOnOutOfMemoryError \
        -XX:HeapDumpPath=/java_dumps/market-batch-prod$(date +-%Y-%m-%d-%H%M%S).hprof \
        -Dfile.encoding=UTF-8 \
        -DlogAppender=STDOUT \
        -Dspring.profiles.active=prod \
        -Duser.timezone=Asia/Seoul \
        -jar market-batch.jar