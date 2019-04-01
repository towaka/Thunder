README
====

这是我于2018年参加毕业答辩时的毕设作品，属于我早期学习java的产物<br>
此项目内包含了游戏里各种物体和背景的图片文件,与其对应的java文件,还有实现奖惩机制的相关接口等<br>
游戏内的爆炸效果采取静态加载逐帧读取的方式<br>
<br><br><br>
游戏方式
-------
游戏方式采用键盘操作<br>
开始游戏为enter键<br>
暂停游戏为空格键<br>
实现射击为Z键<br>
<br>
![](https://github.com/towaka/Thunder/blob/master/src/cn/Thunder/images/start.png)
/<br>


<br>

<br>
本项目类和接口之间的UML图
![](https://github.com/towaka/Thunder/blob/master/src/cn/Thunder/thunder%20uml.png)
/<br>

主程序启动文件为
```Java
ThunderField/src/cn/Thunder/Others/ThunderField.java 
```
飞行物的java文件位于
```Java
ThunderField/src/cn/Thunder/Units
```
实现奖惩机制的接口文件和实现背景循环滚动的文件位于
```Java
ThunderField/src/cn/Thunder/Others/
```
图片文件位于
```Java
ThunderField/src/cn/Thunder/Units/images/
```
