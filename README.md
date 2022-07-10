# Alciphron
Android aplikacija za pracenje vrsta leptira i insekata na teritoriji Srbije koristeci GPS location sistem. Skladistenje lokacija pronadjenih vrsta u bazu podataka
koja moze da se eksportuje u vidu csv ili txt file-a. 

Links to download apk's versions :
  https://mega.nz/file/OboCwZTY#-r4V2thIo6LS3kmsMya9ESc0X0cnUlLS_PjW8ZH7X3M  [version1.1.2]
  https://mega.nz/file/uOxTXayZ#r1BfGq7HaRfobRyirk03S3Q-0ulxTkQHKP4oGTR9Jmc  [version1.0.2]
  https://mega.nz/file/bfJG3YzZ#cfeJELpWyCrYZWSQ-Ykjtt6QlQ3l__2dOnceZ4YP6l4  [version1.0.1]

[version 1.1.2]
   - Promenjen format datuma u yyyy-MM-dd
   - Dodat i kod za svaku vrstu u bazi 
   - U Exportu se umesto imena sad pamti kod vrste
   - U exportu kordinate su prikazane u formatu UTM , koordinate se formatiraju priliko dodavanja vrste i zapisuju u ROOM Database
   - Nadmorska visina zaokruzena na ceo broj
   - Stadijumi dodati u unosu da se moze izabrati stadijum vrste (I - imago, L-larva, P-pupa, O-ova)
   - U export csv i txt fajla dodata i kolona stadijum
   - Validacija unosa , da ne moze da se unese vrsta ukoliko nije neka izabrana
   - Dodat i XLS export

[version 1.0.2]
  - izmenjen naziv Settings item-a u Podesavanja
  - dodat novi meni item Obrisi sve koji brise sve iz liste
  - optimizacija koda za ucitavanje vrsta , sve vrste izvucene u posebnu klasu (initData i initDataRest)

[version 1.0.1]
  - dodato automatsko odredjivanje nadmorske visine lokacije vrste
  - dodata nadmorska visina u export csv i txt file-u 
  - dodata kolona geoAltitude u room database bazi podataka

[version 1.0.0]
  - izabir vrste iz liste vrsta
  - automatsko odredjivanje lokacije koristeci GPS sistem
  - skladistenje vrsta u room database bazu podataka
  - prikaz lokacije izabrane vrste na google maps klikom na odredjenu vrstu iz liste vrsta
  - brisanje lokacije vrste iz liste vrsta
  - export baze u csv i txt file 

