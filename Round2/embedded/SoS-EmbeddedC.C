#include<reg51.h>
#include<stdio.h>// include stdio . h
#include<stdlib.h>// include stdlib . h

unsigned int data_out,command=0x80,temp;

sfr lcd_data_pin=0x80; //P0 port

sbit rs=P2^7; //Register select 

//char cardWord[] = "0013907665";
char flood[] = "Flood Warning";
char earthquake[] = "EarthQuake Warning";
sbit rw=P2^6; //Read/Write 
sbit en=P2^5; //Enable pin
sbit bz=P1^2; //Buzzer
sbit fw=P1^0; //Flood
sbit ew=P1^1; //Earthquake

sbit Data0         =P1^5; //DIO7   
sbit Data1         =P1^4; //DIO6  
int k;
int x;
int y;
int counter;
//unsigned char card_id[12];

void delay(unsigned int count) //Function to provide delay

{

 int i,j;

 for(i=0;i<count;i++)

 for(j=0;j<1275;j++);

}

void lcd_cmd(unsigned char comm) //Lcd command funtion

{

 lcd_data_pin=comm;

 en=1;

 rs=0;

 rw=0;

 delay(1);

 en=0;

}

void lcd_data(unsigned char disp) //Lcd data function

{

 lcd_data_pin=disp;

 en=1;

 rs=1;

 rw=0;

 delay(1);

 en=0;
}

lcd_string(unsigned char *disp) //Function to send string 

{

 int x;

 for(x=0;disp[x]!=0;x++)

 {

 lcd_data(disp[x]);

 }

}

void lcd_init() //Function to initialize the LCD

{

 lcd_cmd(0x38); delay(5);

 lcd_cmd(0x0F); delay(5);

 lcd_cmd(0x80);

 delay(5);

} 

void serial_init(void);
 
unsigned int j;
//Setup the serial port for 9600 baud at 11.0592MHz.
//-------------------------------------------------
void serial_init(void)
{
   SCON = 0x50;                        /* SCON: mode 1, 8-bit UART, enable rcvr        */
   TMOD |= 0x20;               /* TMOD: timer 1, mode 2, 8-bit reload                   */
   TH1   = 0xFD;               /* TH1: reload value for 9600 baud @ 11.0592MHz*/
   TR1   = 1;                 /* TR1: timer 1 run                             */
   TI   = 1;                 /* TI:   set TI to send first char of UART          */
}
 
 
//Delay Routine start here
void delay1(int n)
{
   int i;
            for(i=0;i<n;i++);
}
 
void delay2(int n)
{
   int i;
            for(i=0;i<n;i++)
                        delay1(1000);
}

 

// Mobile Communicatin End

void main()

{
x=0;
counter = 0;
fw=0;
ew=0;
bz=0;


 TMOD=0x20; //Enable Timer 1

 TH1=0XFD;

 SCON=0x50;

 TR1=1; // Triggering Timer 1

 lcd_init();

lcd_cmd(0x81); //Place cursor to second position of first line 
lcd_cmd(0x01); //clear screen
lcd_string("SoS App");

 delay(200);
//recieve();
//SBUF=" ";
lcd_init();
lcd_cmd(0x81); //Place cursor to second position of first line 
//lcd_cmd(0x01); //clear screen
lcd_cmd(0xC1); //Place cursor to second position of second line
//lcd_string("UNIQUE ID:");
//recieve();
 while(1)

 {
 int l;


if (fw == 1 && counter == 0)
{
counter = 1;
bz = 1;
 for(l=0;l<12;l++)

 { 
 
 lcd_data(flood[l]);

 }

  // Mobile communication start
          		
          		serial_init();                                                                                                 //serial initialization
           // LED = 0x00; 
            printf("AT+CMGF=1%c",13);                                        
             delay2(20);  //Text Mode            | hex value of 13 is 0x0D (CR )
            printf("AT+CMGS=\"8939965828\"%c",13); delay2(20);           //Type your mobile number Eg : "9884467058"
           // led_left();                 //scroll left
                        delay1(20);
            printf("Sos-Warning:");                   
              delay2(20);  //Type text as u want
                printf(flood);                   
              delay2(20);  //Type text as u want
            printf("%c",0x1A);                                                             
            delay2(20);  //line feed command

          		// Mobile Communication end
}

if (ew == 1 && counter == 0)
{

counter = 1;
bz =1;
 for(l=0;l<17;l++)

 { 
 
 lcd_data(earthquake[l]);

 }
  // Mobile communication start
          		
          		serial_init();                                                                                                 //serial initialization
           // LED = 0x00; 
            printf("AT+CMGF=1%c",13);                                        
             delay2(20);  //Text Mode            | hex value of 13 is 0x0D (CR )
            printf("AT+CMGS=\"8939965828\"%c",13); delay2(20);           //Type your mobile number Eg : "9884467058"
           // led_left();                 //scroll left
                        delay1(20);
            printf("Sos-Warning:");                   
              delay2(20);  //Type text as u want
                printf(earthquake);                   
              delay2(20);  //Type text as u want
            printf("%c",0x1A);                                                             
            delay2(20);  //line feed command

          		// Mobile Communication end
}
 

 
}
 } 