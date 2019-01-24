# 図書館の予約管理システム

学部2年次に開校されているプログラミングの講義の最終課題「オリジナルのデータベースを使用したWebアプリケーションを作成」で作成したWebアプリケーションである．

このリポジトリは実際に筆者(masa-ramenO)が課題として，当時提出したものをアップロードしたものである．

## 使用した環境

## OS

- Windows 10 Home(64bit)

### プログラミング言語

- Java
- HTML

本アプリケーションはJavaサーブレットおよびjspを使用している．

### Java開発環境

- Java(TM) SE Runtime Envionment (build 1.8.0_181-b13)
- NetBeans IDE 8.2

### データベース

- JavaDB

## 開発における反省点

本アプリケーション開発にあたっての反省点をあらかじめ列挙しておく．

- 複数ファイルにおいて同一の処理が記述されている．(同一処理をまとめていない)
    - (例) DBへの接続が複数のファイルに記述されている．(接続に関する処理は同一であるため，メソッドとしてまとめるべきだった)
- 登録内容の確認ページを一部にしか実装できなかった．

## データベースの構造

作成したデータベースを以下に示す．

### book_library

蔵書情報に関するデータベース．

| フィールド名 | 内容 | 型 | 備考 |
|:-|:-|:-|:-|
| isbn | ISBNコード | BIGINT | Primary Key |
| title | タイトル | varchar(255) | |
| author | 著者 | varchar(255) | |
| publisher | 出版社 | varchar(255) | |
| publication_year | 出版年 | Integer| |
| holding_count | 蔵書数 | Integer |

### library_user

ユーザー情報に関するデータベース．

| フィールド名 | 内容 | 型 | 備考 |
|:-|:-|:-|:-|
| user_num | 管理番号 | Integer | Primary Key，自動付与 |
| user_name | 氏名 | varchar(255) |  |
| id | ユーザーID | varchar(15) | unique |
| password | パスワード | varchar(15) | |
| overdue_count | 延滞回数 | Inetger | |

### reserve

予約状況に関するデータベース．

| フィールド名 | 内容 | 型 | 備考 |
|:-|:-|:-|:-|
| reserve_num | 予約番号 | Integer | Primary Key，自動付与 |
| user_num | 予約者の管理番号 | Integer | library_userのuser_numberと同一データ |
| isbn | ISBNコード | BIGINT | book_libraryのisbnと同一データ |
| reserve_date | 予約日 | Date | |