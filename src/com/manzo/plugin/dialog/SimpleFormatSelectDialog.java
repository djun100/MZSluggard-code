package com.manzo.plugin.dialog;

import com.cy.util.UtilPlugin;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.psi.PsiFile;
import com.manzo.plugin.bean.AndroidView;
import com.manzo.plugin.bean.CheckHeaderCellRenderer;
import com.manzo.plugin.bean.AndroidViewsTableModel;
import com.manzo.plugin.controller.SimpleFileController;
import com.manzo.plugin.utils.AndroidUtils;
import org.jdesktop.swingx.JXTable;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.event.*;
import java.util.List;

/**
 * 生成activity交互dialog
 */
public class SimpleFormatSelectDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JScrollPane mScrollTable;
    private List<AndroidView> mAndroidViews;

    private final AnActionEvent mAnActionEvent;

    public static void showDialog(AnActionEvent anActionEvent) {
        String layoutName = UtilPlugin.getSelectedText(anActionEvent);
        PsiFile xmlFile = UtilPlugin.getFirstPsiFileByFileName(anActionEvent,layoutName+".xml");

        if (xmlFile == null) {
            return;
        }
        //获取到layout中的view对象
        List<AndroidView> androidViews = AndroidUtils.getAndroidViewsFromXML(xmlFile);

        SimpleFormatSelectDialog editDialog = new SimpleFormatSelectDialog(anActionEvent, androidViews);
        editDialog.setSize(600, 360);
        editDialog.setLocationRelativeTo(null);
        editDialog.setResizable(false);
        editDialog.setVisible(true);
    }

    public SimpleFormatSelectDialog(AnActionEvent anActionEvent, List<AndroidView> androidViews) {
        this.mAnActionEvent=anActionEvent;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        this.mAndroidViews = androidViews;
        initViewTable(mAndroidViews);
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void initViewTable(List<AndroidView> androidViews) {
        AndroidViewsTableModel myModel = new AndroidViewsTableModel(androidViews);

        // JTable
        JXTable table = new JXTable(myModel);
        // 获得表格的表格列类
        TableColumn columnChoice = table.getColumnModel().getColumn(AndroidViewsTableModel.CHOICE_BOX_INDEX);
        TableColumn columnClick = table.getColumnModel().getColumn(AndroidViewsTableModel.CLICK_BOX_INDEX);

        // 实例化JCheckBox
        columnChoice.setCellEditor(new DefaultCellEditor(new JCheckBox()));
        columnChoice.setPreferredWidth(60);
        columnClick.setCellEditor(new DefaultCellEditor(new JCheckBox()));
        columnClick.setPreferredWidth(60);
        table.getTableHeader().setDefaultRenderer(new CheckHeaderCellRenderer(table));
        // 获得自定义的抽象表格模型
        mScrollTable.setViewportView(table);
    }

    private void onOK() {
        // add your code here
        SimpleFileController.loadFile(mAnActionEvent, mAndroidViews);
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
