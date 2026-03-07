# Kindクラスター×ingress-nginx
## IngressコントローラーのIngress振り分けの仕組み（前提）
```
1. http://example.comにアクセスする。 ⇒この時、HTTPのHostヘッダーにはexample.comが記載される。

2. DNSによってexample.comが名前解決され、example.comに対応するIPアドレスを持つサーバーにアクセスする。

3. IngressコントローラーはHostヘッダーのexample.comを取得し、spec.rules[].host: example.comのIngressにルーティングする。
```

## ロードバランサーとIngressコントローラーの関係
### ロードバランサー：Ingressコントローラーに外部からと到達できる経路を提供
⇒クラウド環境ではクラウドロードバランサー(ELBなど)⇒Ingressコントローラーという経路が前提であるため、ロードバランサーが必要。

## Kindクラスター×ingress-nginxの場合
### 経路は以下の通り
```
localhost:<コンテナに紐づくホストOSのポート番号>
   ↓
Podman port forward
   ↓
kindノードコンテナ:<コンテナのリッスンポート番号>
   ↓
Ingress Controller
```
⇒Podmanのポートフォワーディングがロードバランサーの代わりをしている。