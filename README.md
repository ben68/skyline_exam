## Android新人練習題TopMusic from itunes

### 專案需求如下：
+ 參考https://rss.itunes.apple.com/en-us API取得 itunes top 100 music 的內容。

+ 表列出Top 100 music的結果。

+ 列表上至少需加入：
   > + 該音樂的圖片<br>
   > + 該音樂的名稱<br>
   > + 該音樂的歌手

+ 能在個別的音樂上加入自己的筆記，筆記可加入： 
   > + 文字<br>
   > + 圖片(自External storage存取及拍照)<br>
   > + 影片(自External storage存取及錄影)<br>

+ 音樂列表由API所取得的資料內先存入DB，layout再由DB取出 (離線存取機制)。
+ 筆記的內容存入DB，其中圖片與影片的部分為檔案路徑。


### 技術規格：
+ 程式語言：Kotlin
+ 網路：Retrofit
+ 架構調整：ReactiveX(RxJava and RxAndroid)
+ DB：Room
+ 程式架構：MVVM
+ 建立 Unit Tests

> 注意！以上沒指定的則可任意使用
