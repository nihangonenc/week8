package core;
import javax.swing.*;
import java.awt.*;
import javax.print.attribute.standard.JobStateReason;


public class Helper {
    public static void setTheme(){ //tema belirler

        for (UIManager.LookAndFeelInfo info: UIManager.getInstalledLookAndFeels()){
            if("Nimbus".equals(info.getName())){
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (Exception e){
                    System.out.println(e.getMessage());
                }
                break;
            }
        }
    }
    public static void showMsg(String str){ //kullanıcıya gösterilecek mesajlar yazıldı DEĞERLENDİRME FORMU 24-25
        optionPaneTR();
        String msg;
        String title;

        switch (str){
            case "fill":
                msg = "Lütfen tüm alanları doldurunuz.";
                title = "Hata!";
                break;
            case "done":
                msg = "İşlem başarılı";
                title = "Sonuç";
                break;
            case "notFound":
                msg = "Kayıt bulunamadı";
                title = "Bulunamadı";
                break;
            case "error":
                msg = "Hatalı işlem yaptınız";
            default:
                msg = str;
                title = "Hata";

        }
        JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);

    }
    public static boolean confirm(String str){
        optionPaneTR();
        String msg;
        if(str.equals("sure")){
            msg = "Bu işlemi yapmak istediğinize emin misiniz?";
        }else {
            msg = str;
        }
        return JOptionPane.showConfirmDialog(null,msg,"Emin misin?", JOptionPane.YES_NO_OPTION) == 0;
    }

    public static boolean isFieldEmpty(JTextField field){ //metin alanlarının boş olup olmadığının kontrolü
        return field.getText().trim().isEmpty();
    }
    public static boolean isFieldListEmpty(JTextField[] fieldList){ //listenin boş olup olmadığının kontrolü
        for (JTextField field: fieldList){
            if (isFieldEmpty(field)){
                return true;
            }
        }
        return false;
    }
    public static int getLocationPoint(String type, Dimension size){ //çıkacak pencereleri ekranın ortasına sabitler
        return switch (type) {
            case "x" -> (Toolkit.getDefaultToolkit().getScreenSize().width - size.width) / 2;
            case "y" -> (Toolkit.getDefaultToolkit().getScreenSize().height - size.height) / 2;
            default -> 0;
        };
    }
    public static void optionPaneTR(){ //butonlardaki ingilizce uyarıları türkçeleştirme
        UIManager.put("OptionPane.okButtonText", "Tamam");
        UIManager.put("OptionPane.yesButtonText", "Evet");
        UIManager.put("OptionPane.noButtonText", "Hayır");
    }


}
