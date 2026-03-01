# Podmanコンテナレジストリ⇒Kindクラスターへのイメージのロード
### podman saveでコンテナイメージをtarアーカイブ
```
podman save <イメージ名> -o <tarファイル名>.tar
```
#### 例）
```
podman save localhost/api:v5 -o api.tar
```

### kind loadでKindクラスターにイメージをロード
```
kind load image-archive <tarファイル名>.tar --name <クラスタ名>
```
#### 例）
```
kind load image-archive api.tar --name my-cluster
```

### Kindクラスタ上でイメージを操作
```
# 参照
ctr -n k8s.io images list

# 削除
ctr -n k8s.io images rm <イメージ名>
（例）
ctr -n k8s.io images rm localhost/api:v5
```

### pod.spec.containers[].imageに記載するイメージ名⇒ctr -n k8s.io images listで出力したリストのREF列
#### 例）
```
spec:
    containers:
    - name: api
      image: localhost/api:v5 
```