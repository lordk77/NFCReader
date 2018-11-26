package io.ticketcoin.dashboard.dto;
import java.io.*;
import java.util.*;
import javax.smartcardio.*;

public class CardTest {

  final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

  public static String bytesToHex(byte[] bytes) {
    char[] hexChars = new char[bytes.length * 2];
    int v;
    for ( int j = 0; j < bytes.length; j++ ) {
      v = bytes[j] & 0xFF;
      hexChars[j * 2] = hexArray[v >>> 4];
      hexChars[j * 2 + 1] = hexArray[v & 0x0F];
    }
    return new String(hexChars);
  }

  public static void main(String[] args) throws Exception {
    TerminalFactory factory = TerminalFactory.getInstance("PC/SC", null);
    System.out.println(factory);

    List<CardTerminal> terminals = factory.terminals().list();
    System.out.println("Terminals: " + terminals);
    if (terminals.isEmpty()) {
      throw new Exception("No card terminals available");
    }

    CardTerminal terminal = terminals.get(0);
      
    // Keep looping looking for cards until the application is closed
    while( true )
    {
      terminal.waitForCardPresent( 0 );
      try {
    	  // establish a connection with the card
  	    Card card = terminal.connect("*");
  	    System.out.println("card: " + card);
  	    CardChannel channel = card.getBasicChannel();
  	    //ResponseAPDU r = channel.transmit(new CommandAPDU(c1));
  	    //System.out.println("response: " + toString(r.getBytes()));

  	    CommandAPDU command = null;
  	    ResponseAPDU response = null;
  	    
  	    
  	    //ACR122U Load Authentication Keys
          command = new CommandAPDU(new byte[]{(byte)0xFF,(byte)0x82,(byte)0x00,(byte)0x00,(byte)0x06,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF,(byte)0xFF});
          response = channel.transmit(command);
          System.out.println( new String( response.getData() ) );
          
  	    //ACR122U Authentication
          command = new CommandAPDU(new byte[]{(byte)0xFF,(byte)0x86,(byte)0x00,(byte)0x00,(byte)0x05,(byte)0x01,(byte)0x00,(byte)0x00,(byte)0x60,(byte)0x00 });
          response = channel.transmit(command);
          System.out.println( new String( response.getData() ) );
          
          
  	    //reads block 0
  	    command = new CommandAPDU(new byte[]{(byte)0xFF,(byte)0xB0,(byte)0x00,(byte)0x01,(byte)0x10});
  	    response = channel.transmit(command);

          byte[] byteArray = response.getData();
          System.out.println( new String( byteArray ) );
          
          
       // disconnect
  	    card.disconnect(false);
        
        
        Thread.sleep(5000);
      } catch (CardException e) {
        e.printStackTrace();
      }
    }
  }
}