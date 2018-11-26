import java.awt.Color;
import java.util.List;

import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;
import javax.smartcardio.TerminalFactory;
import javax.swing.JFrame;

import io.ticketcoin.nfc.core.Reader;
import io.ticketcoin.nfc.core.Reader.ReaderListener;
import io.ticketcoin.nfc.display.LED;
import io.ticketcoin.rest.response.TicketDTOResponseWrapper;
import io.ticketcoin.util.RSClient;


@SuppressWarnings("restriction")
public class NFCReader implements ReaderListener {  
    
	public static final LED led=new LED(); 
	public static RSClient client= null;
	
  
    public static void main(String[] args)  {
    	
    	client = new RSClient(args[0], args[1]);
    	client.authenticate();
    	
        JFrame f = new JFrame("main");
        f.setSize(400, 300);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(led);  
        f.setVisible(true);
        

        
        Reader reader;
		try {
			reader = new Reader(connectToTerminal());
			reader.addCardListener(new NFCReader());
		} catch (CardException e) {
			e.printStackTrace();
			System.exit(1);
		}
        
        
        
    }
    
    private static CardTerminal connectToTerminal() throws CardException
    {
		TerminalFactory factory = TerminalFactory.getDefault();
	    List<CardTerminal> terminals = factory.terminals().list();
	    System.out.println("Terminals: " + terminals);
	    return terminals.get(0);
    }

	@Override
	public void inserted(Card card) {
		
		try 
		{
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
	        String cardID = new String( byteArray );
	        System.out.println(cardID);
	        
	        TicketDTOResponseWrapper res = client.consumeCard(cardID);
	        if(res.isSuccess())
	        		led.setBackground(Color.GREEN);
	        else
	        		led.setBackground(Color.RED);
	        
	        
		}
		catch (Exception e) {
			e.printStackTrace();
			led.setBackground(Color.RED);
		}
        
		
	}

	@Override
	public void removed() {
		led.setBackground(Color.WHITE);
	}
}