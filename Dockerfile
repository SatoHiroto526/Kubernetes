# ベースイメージはWildfly
FROM quay.io/wildfly/wildfly:latest

# ※Podman環境テスト用
# USER root:root
# RUN mkdir -p /secret && \
#     echo "password" > /secret/secret.txt && \
#     chown jboss:jboss -R /secret

# ユーザーをjboss:jbossに切り替え 
# ※Wildflyのベースイメージではデフォルトでjboss:jbossが存在するし、多分デフォルトでjboss:jbossになっている。
USER jboss:jboss

# ※Podman環境テスト用
# ENV ENV=EnvData!!!

# 環境変数設定
ENV JBOSS_HOME=/opt/jboss/wildfly

# ./target/Container-0.0.1-SNAPSHOT.warを$JBOSS_HOME/standalone/deployments/配下にコピー
COPY ./target/Kubernetes-0.0.1-SNAPSHOT.war $JBOSS_HOME/standalone/deployments/Kubernetes-0.0.1-SNAPSHOT.war
