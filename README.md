[![Build Status](https://travis-ci.org/MastersOfDesaster/Vongpiler.svg?branch=master)](https://travis-ci.org/MastersOfDesaster/Vongpiler)
# Vongpiler

Halo i bim 1 Vongpiler vong [2k18-spec](https://github.com/MastersOfDesaster/2k18-spec) her.

## Danksagung

Für unserem Mutis!

## Download

https://github.com/MastersOfDesaster/Vongpiler/releases/latest

## Vongpiler

Der Vongpiler ermöglicht das übersetzen des mit der [2k18-spec](https://github.com/MastersOfDesaster/2k18-spec) geschriebenen Quellcodes in Machschinencode zum ausführen in der [VRE (Vong Runtime Environment)](#vre).
Die Dateiendung des Quellcodes ist .vsh was für "vong skript her" steht.

### Aufruf

In der Kommandozeile kann ein Programm mit folgendem Befehl ausgeführt werden:

 ```shell
 java -jar vongc.jar [Dateiname.vsh]
 ```
 
 Eine Hilfe zur Bedienung erhält man mit 
 
 ```shell
 java -jar vongc.jar -h
 ``` 

<a name="vre"/>

## VRE (Vong Runtime Environment)

Die VRE ermöglicht die Ausführung von kompiliertem vongscript code (.vch).
Dazu ist die VVM (Vong Virtual Machine) integriert, die den Maschinencode in der Java JVM ausführt.

### Aufruf

In der Kommandozeile kann ein Programm mit folgendem Befehl ausgeführt werden:

```shell
java -jar vong.jar [Dateiname.vch]
```

Eine Hilfe zur Bedienung erhält man mit 

```shell
java -jar vong.jar -h
```

## Dokumentation

Siehe [2k18-spec](https://github.com/MastersOfDesaster/2k18-spec)

## Beispielcode

### Fibonacci Zahlen

```2k18
was ist das für 1 code?
  i bims 1 zal lauch1 gönn dir 0!!!
  i bims 1 zal lauch2 gönn dir 1!!!
  i bims 1 zal erg gönn dir 0!!!
  i bims 1 zal max gönn dir 0!!!
  i bims 1 zal zähl gönn dir 0!!!
  i bims 1 isso ende gönn dir nope!!!

  gieb "gieb anz fibonacci" her?
  max gönn dir 1gabe!!!

  #start
  ende gönn dir was ist das für 1 isweniga vong zähl, max her?
  bist du ende? yup
      erg gönn dir was ist das für 1 sume vong lauch1, lauch2 her?
      gieb "Fibonacci " + zähl +" :" + lauch1 + " + " + lauch2 + " = " + erg her?
      lauch1 gönn dir lauch2!!!
      lauch2 gönn dir erg!!!
      zähl gönn dir was ist das für 1 sume vong zähl, 1 her?
      g zu #start du larry!!!
  real rap
1 nicer!!!
```

## Was isd das für ein Vongpiler vong Erschafung her?

* Am **firsten day** createte Vongpiler teams 1 gramatik vong 2k18 spec her und sah das es wundern🍦e war.

* Am **seconden day** ward 1 virtual steakmachine definirt. Vongpiler teams dankt hirzu Terence Paar für 1 gutes Tutorial. 

* Am **thirden day** makte Vongpiler teams 1 virtual steakmachine vong Färtiggestelung her. Für Vongpilirung vong codeerzeugung her, wurden lexer u paser färtig gem8.

* Am **fourthen day**  wurd codeerzeugung gefinished u 1stes mahl quälcode verpeilert und ausgeführt. "halo i bims!!!" speakte firstes code zu uns.

* Am **fifften day** chekte Vongpiler teams "Bist du?" und "#" war neeted vong jumps her. Vongpiler konte nun kras jumpen u äntscheidungem träfen.

* Am **sixten day** buildete Vongpiler teams "1gabe" von komunikation her. So konte vongpiler teams zu program sprächen.

* Am **seventeen day** checkte Vongpiler teams das ergenis, das es gebuildet hat, mit lexer u parse, u code vong erzeugung her, u Vong Runtime Environment vong auführung her.  
   U Vongpiler team sayte:  
   **<<was is das für ein 1 n🍦er code?>>**
