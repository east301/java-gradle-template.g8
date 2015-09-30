###
### automated build of $name$ by docker
###

FROM java:8

RUN mkdir /opt/$name$

ADD . /opt/$name$-source
RUN cd /opt/$name$-source \
  && ./gradlew clean \
  && ./gradlew jar \
  && ./gradlew :resource:generateDependencyLicenseReport \
  && ./gradlew :$name$:distZip :$name$:distTar

RUN cd /opt/$name$ \
  && tar xvf /opt/$name$-source/$name$/build/distributions/$name$-*.tar --strip 1
