package swing.app.components;

import com.formdev.flatlaf.FlatClientProperties;
import net.miginfocom.swing.MigLayout;
import raven.drawer.component.header.SimpleHeader;
import raven.drawer.component.header.SimpleHeaderData;
import raven.drawer.component.menu.*;
import raven.drawer.component.menu.data.Item;
import raven.swing.AvatarIcon;
import raven.swing.blur.BlurChild;
import raven.swing.blur.style.GradientColor;
import raven.swing.blur.style.Style;
import raven.swing.blur.style.StyleBorder;
import raven.swing.blur.style.StyleOverlay;
import swing.app.components.Forms.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

public class SystemMenu extends BlurChild {

    public SystemMenu(){
        super(new Style()
                .setBlur(30)
                .setBorder(new StyleBorder(10)
                        .setOpacity(0.15f)
                        .setBorderWidth(1.2f)
                        .setBorderColor(new GradientColor(new Color(200,200, 200), new Color(150, 150, 150), new Point2D.Float(0, 0), new Point2D.Float(1f, 0)))
                )
                .setOverlay(new StyleOverlay(new Color(0, 0, 0), 0.2f))
        );
        init();
    }

    private void init(){
        setLayout(new MigLayout("wrap, fill", "[fill]","[grow 0][fill]"));
        simpleMenu = new SimpleMenu(getMenuOption());

        simpleMenu.setOpaque(false);
        JScrollPane scrollPane = new JScrollPane(simpleMenu);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getVerticalScrollBar().setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(10);
        scrollPane.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE, "" +
                "trackArc:999;" +
                "width:5;" +
                "thumbInsets:0,0,0,0");

        SimpleHeader header = new SimpleHeader(getHeaderData());
        header.setOpaque(false);
        add(header);
        add(scrollPane);
    }

    private SimpleHeaderData getHeaderData(){
        return new SimpleHeaderData()
                .setTitle("WebMercado")
                .setDescription("Loja Virtual")
                .setIcon(new AvatarIcon(getClass().getResource("/swing/images/profile.png"), 60, 60, 999));
    }

    private SimpleMenuOption getMenuOption() {
        raven.drawer.component.menu.data.MenuItem items[] = new raven.drawer.component.menu.data.MenuItem[]{
                new Item.Label("MAIN"),
                new Item("USUÁRIO", "dashboard.svg"),
                new Item.Label("CLIENTES"),
                new Item("Dados dos Clientes", "email.svg"),
                new Item("Modificar Dados", "edit.svg"),
                new Item.Label("PRODUTOS"),
                new Item("Dados dos Produtos", "chat.svg"),
                new Item.Label("PAGAMENTOS"),
                new Item("Dados dos Pagamentos", "calendar.svg"),
                new Item.Label("ORDEM DE COMPRA"),
                new Item("Produtos a Comprar", "shop.svg"),
                new Item.Label("HISTÓRICO"),
                new Item("Histórico de Modificação", "history.svg")
                        .subMenu("Tabela Cliente")
                        .subMenu("Tabela Produto")
                        .subMenu("Tabela Pagamento"),
                new Item.Label("CONFIGURAÇÕES"),
                new Item("Configurações", "ui.svg")
                        .subMenu("Cor"),
                new Item.Label("SOBRE"),
                new Item("Sobre", "forms.svg")
                        .subMenu("Autores")
                        .subMenu("SAC")
                        .subMenu("Relatar Problema")
        };

        return new SimpleMenuOption()
                .setBaseIconPath("swing/menu")
                .setIconScale(0.03f)
                .setMenus(items)
                .setMenuStyle(new SimpleMenuStyle() {
                    @Override
                    public void styleMenuPanel(JPanel panel, int[] index) {
                        panel.setOpaque(false);
                    }

                    @Override
                    public void styleMenuItem(JButton menu, int[] index) {
                        menu.setContentAreaFilled(false);
                    }
                })
                .addMenuEvent(new MenuEvent() {
                    @Override
                    public void selected(MenuAction menuAction, int[] ints) {
                        System.out.print("Menu select");
                        if (ints.length == 1) {
                            int index = ints[0];
                            if (index == 3) { // CLIENTES
                                FormManager.getInstance().showForm("Dados dos Clientes", new ClienteForm());
                            } else if (index == 5) { // PRODUTOS
                                FormManager.getInstance().showForm("Dados dos Produtos", new ProdutoForm());
                            } else if (index == 7) { // PAGAMENTOS
                                FormManager.getInstance().showForm("Dados dos Pagamentos", new PagamentoForm());
                            } else if (index == 9) { // ORDEM DE COMPRA
                                FormManager.getInstance().showForm("Produtos a Comprar", new OrdemForm());
                            } else if (index == 11) { // HISTÓRICO
                                FormManager.getInstance().showForm("Histórico de Modificação", new HistoricoForm());
                            } else if (index == 13) { // CONFIGURAÇÕES
                                // Código para abrir a forma de configurações
                            } else if (index == 15) { // SOBRE
                                FormManager.getInstance().showForm("Sobre", new SobreForm());
                            }
                        }
                    }
                });
    }

    private SimpleMenu simpleMenu;

}