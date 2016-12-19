package com.company;

import javafx.scene.image.*;
import javafx.scene.transform.Scale;

import javax.swing.*;
import java.awt.*;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Main extends JPanel implements ActionListener {

    public static JFrame jFrame;
    public static final int SCALE = 32;  // размер клетки
    public static final int WIDTH =  20; // ширина окна
    public static final int HEIGHT = 20; // Высота окна
    public static int SPEED = 5; // скорость змейки

    Image telo = new ImageIcon("Res\\Тело.jpg").getImage();

    Timer timer = new Timer(1000/SPEED, this); // обновление экрана

        Apple apple = new Apple( (int) (Math.random() * WIDTH ), (int) (Math.random() * WIDTH ) ); // позиция яблока
    Snake s = new Snake(7,8,7,7); // координаты змейки


    public Main(){ // конструктор
        timer.start(); // сратр обновлений экрана
        addKeyListener(new KeyBoard()); // запускает обработчик клавиатуры
        setFocusable(true); //
    }

    Golova golova = new GolovaRight();
    public Image getgolovaImage(){
        return golova.golovaImage;
    }

    ColorApple colorApple = new AppleRed();
    public Image getappleImage(){ // беры цвет яблока у класса ColorApple
        return colorApple.appleImage;
    }

    Random rand = new Random();
    int i = rand.nextInt(2)+1;

    Background background = new BackgroundTrava();
    public Image getfonImage(){
        if( i == 1){
            background = new BackgroundTrava();
        }
        if(i == 2){
            background = new BackgroundZemlya();
        }
        return background.fonImage;
    }




    public void paint(Graphics g){ // отрисовка

        //for( int x=0;x<WIDTH*SCALE;x+=SCALE){ // цикл отрисовки вертикальных линий
           // g.setColor(Color.red); // цвет лиций (красный)
           // g.drawLine(x,0,x,WIDTH*SCALE); // координаты отрисовки линий
       // }

     //   for(int y=0;y<HEIGHT*SCALE;y+=SCALE){ // цикл отрисовки горизонтальных линий
         //   g.setColor(Color.red); // цвет линий (красный)
         //   g.drawLine(0,y,HEIGHT*SCALE,y); // координаты отрисовки линий
       // }
        g.drawImage(getfonImage(),0,0, null); // фон

        g.drawImage(getappleImage(),apple.posX*SCALE, apple.posY*SCALE, null); // яблоки

        for( int l = 1; l < s.lenght; l++){ // цикл отрисовки змейки
            g.drawImage(telo, s.sX[l]*SCALE, s.sY[l]*SCALE,null); // коордтнаты отрисовки змейки
        }
        for( int l = 0; l < 1; l++) { // цикл отрисовки головы змейки
            g.drawImage(getgolovaImage(), s.sX[l] * SCALE, s.sY[l] * SCALE, null); // коордтнаты отрисовки головы змейки
        }

    }

    public static void main(String[] args) {
        jFrame = new JFrame("Snake"); // инициализируем
        jFrame.setSize(WIDTH*SCALE+6, HEIGHT*SCALE+28); // размер окна
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // при закрытии что б программа останавливалась
        jFrame.setResizable(false); // отключения ростагивания окна
        jFrame.setLocationRelativeTo(null); // размещение по середине
        jFrame.add(new Main()); // вызываем через конструктор
        jFrame.setVisible(true); // делаем окно видимым
    }

    @Override
    public void actionPerformed(ActionEvent e) { // событие таймера
        s.move(); // передаем направление движения змейки
        if((s.sX[0] == apple.posX)&&(s.sY[0]) == apple.posY ){ // если голова змейки ест яблоко то змейка увеличивается
            apple.setRandomPosition(); // изменяется позиция яблока
            s.lenght++; // увеличение змейки

            Random rand = new Random();
            int i = rand.nextInt(3)+1; // Берется случайное число  от 1 до 3
            if(i == 1){ // если выбрано число 1 то яблоко зеленое
                colorApple=new AppleGreen();
            }
            if(i == 2){ // если выбрано число 2 то яблоко красное
                colorApple =new AppleRed();
            }
            if(i == 3){ // если выбнаро число 3 то яблоко красное
                colorApple = new AppleYellow();
            }
        }
        for( int l = 1; l < s.lenght; l++){
            if((s.sX[l] == apple.posX)&&(s.sY[l]) == apple.posY ){ // проверка, на то что бы яблоко не появлялось на змейке
             apple.setRandomPosition(); // если яблоко появится на змейке то змейка не увеличится, а яблоко поменяет позицию
            }
            if((s.sX[0] == s.sX[l])&&(s.sY[0] == s.sY[l])){ //
                timer.stop();
                JOptionPane.showMessageDialog(null,"Вы проиграли, начать заново?");
                jFrame.setVisible(false);
                s.lenght =2;
                s.direction = 1;
                apple.setRandomPosition();
                jFrame.setVisible(true);
                timer.start();

            }
        }

        repaint(); // постоянная перересовка
    }
    public class KeyBoard extends KeyAdapter{  // обработчик событий клавиаруты
        public void keyPressed (KeyEvent event){ // обработчик события нажатия клавиши
            int key = event.getKeyCode();

            if((key == KeyEvent.VK_UP)&&(s.direction != 2)){
                golova = new GolovaUp();
                s.direction = 0; // управление вверх
            }
            if((key == KeyEvent.VK_RIGHT)&&(s.direction != 3)){
                golova = new GolovaRight();
                s.direction = 1; // управление вправо
            }
            if((key == KeyEvent.VK_DOWN)&&(s.direction != 0)){
                golova = new GolovaDown();
                s.direction = 2; // управление вниз
            }
            if((key == KeyEvent.VK_LEFT)&&(s.direction != 1)){
                golova = new GolovaLeft();
                s.direction = 3; // управление влево
            }

        }
    }
}
