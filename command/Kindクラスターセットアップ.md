# Kindクラスターセットアップ手順
## Podman DesktopでKindクラスターを作成
```
Podman Desktop > Settings > Resources > Create a new Kind cluster
```
### 以下のようなクラスターができる
```
<cluster名>-control-plane
```
#### 例）my-clusterという名前でクラスターを作成した場合
```
クラスタ名：my-cluster
ノード名：my-cluster-control-plane
※Podman上のコンテナ名：ノード名（上記の場合はmy-cluster-control-plane）
```

## 必要なライブラリのインストール（ホスト側） ※ホスト側wslで以下コマンドを実行
### kind
```
# インストール
curl -Lo ./kind https://kind.sigs.k8s.io/dl/latest/kind-linux-amd64

# 権限変更
chmod +x ./kind

# 移動
sudo mv ./kind /usr/local/bin/kind

# 確認
kind --version

# KindにPodmanを使わせる設定　※デフォルトはDocker
export KIND_EXPERIMENTAL_PROVIDER=podman
```

## 必要なライブラリのインストール（Kindクラスター側） ※クラスターコンテナにpodman execで接続して以下コマンドを実行
### vi
```
apt update
apt install -y vim
```

## Ingressコントローラーを作成
### ingress-nginxをapply ※namespaceはingress-nginx
```
kubectl apply -f https://raw.githubusercontent.com/kubernetes/ingress-nginx/main/deploy/static/provider/cloud/deploy.yaml
```
### 起動確認
```
kubectl get pod -n ingress-nginx
```
### 上記で作成したService（ingress-nginx-controller）の向き先をClusterIPに変更
```
# デフォルト値確認
kubectl get svc -n ingress-nginx
（出力例）
NAME                                 TYPE        CLUSTER-IP     EXTERNAL-IP   PORT(S)          AGE
ingress-nginx-controller            LoadBalancer   10.96.169.53   <none>        80/TCP,443/TCP   38m
ingress-nginx-controller-admission   ClusterIP   10.96.34.244   <none>        443/TCP          38m

# spec.ports[].typeをLoadBalancer⇒ClusterIPに変更（向き先をClusterIPに変更）
kubectl edit svc ingress-nginx-controller -n ingress-nginx
（変更例）
type: LoadBalancer
↓
type: ClusterIP

# 確認
kubectl get svc -n ingress-nginx
（出力例）
NAME                                 TYPE        CLUSTER-IP     EXTERNAL-IP   PORT(S)          AGE
ingress-nginx-controller             ClusterIP   10.96.169.53   <none>        80/TCP,443/TCP   38m
ingress-nginx-controller-admission   ClusterIP   10.96.34.244   <none>        443/TCP          38m
```

## KindノードへアクセスするためのIPアドレス確認
```
[user@DESKTOP-JJR9BVI ~]$ podman port my-cluster-control-plane
80/tcp -> 0.0.0.0:9090
443/tcp -> 0.0.0.0:9443
6443/tcp -> 127.0.0.1:54244

※上記例だとホストOSの9090番ポートはKindクラスターの80番ポートにルーティングされる
⇒http://localhost:9090をホストOSで叩くとKindノードの80番にアクセスできる
```