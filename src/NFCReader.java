import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.smartcardio.Card;
import javax.smartcardio.CardChannel;
import javax.smartcardio.CardException;
import javax.smartcardio.CardTerminal;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;
import javax.smartcardio.TerminalFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import io.ticketcoin.dashboard.dto.TicketDTO;
import io.ticketcoin.nfc.core.Reader;
import io.ticketcoin.nfc.core.Reader.ReaderListener;
import io.ticketcoin.rest.response.TicketDTOResponseWrapper;
import io.ticketcoin.util.RSClient;


@SuppressWarnings("restriction")
public class NFCReader implements ReaderListener {  
    
	public static RSClient client= null;
	
	public static final JPanel led = new JPanel(); 
	public static final JLabel title = new JLabel("");
	public static final JLabel centralLabel = new JLabel("");
  
    public static void main(String[] args)  {
    	
    	client = new RSClient(args[0], args[1]);
    	client.authenticate();
    	
        JFrame f = new JFrame("NFC Reader");
      
        led.setBounds(0,0,800,400);    
        led.setBackground(Color.gray);  
        title.setBounds(0,0,800,30);    
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBackground(Color.yellow);   
        title.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 50));
             
        centralLabel.setBounds(0,100,800,30);    
        centralLabel.setBackground(Color.yellow);   
        centralLabel.setHorizontalAlignment(SwingConstants.CENTER);
        centralLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 60));
     
        
        led.add(title); 
        led.add(centralLabel);  
        led.setBackground(Color.WHITE);
        
        led.setLayout(new GridLayout(4,1));  
        
        
        f.add(led);  
                f.setSize(800,400);    
                f.setLayout(null);    
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
	        {
	        		TicketDTO tDTO = (TicketDTO)res.getData();
		    		title.setText(tDTO.getEventDetail().getName());
		    		centralLabel.setText(tDTO.getAllowedEntrances().toString()); 
	        		led.setBackground(Color.GREEN);
	        }
	        else
	        {
				title.setText("Accesso negato");
				centralLabel.setText(""); 
	        		led.setBackground(Color.RED);
	        }
	        
		}
		catch (Exception e) {
			e.printStackTrace();
			title.setText("Accesso negato");
			centralLabel.setText(""); 
			led.setBackground(Color.RED);
		}
        
		
	}

	@Override
	public void removed() {
		title.setText("");
		centralLabel.setText(""); 
		led.setBackground(Color.WHITE);
	}
}