package com.cy.util;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;

public class UtilPlugin {

    public static Project getProject(AnActionEvent anActionEvent){
        Project fatherProject = anActionEvent.getProject();
        return fatherProject;
    }

    public static Editor getEditor(AnActionEvent anActionEvent){
        Editor editor = CommonDataKeys.EDITOR.getData(anActionEvent.getDataContext());
        return editor;
    }

    public static PsiFile getPsiFile(AnActionEvent anActionEvent){
        PsiFile file = anActionEvent.getData(PlatformDataKeys.PSI_FILE);
        return file;
    }

    public static String getSelectedText(AnActionEvent anActionEvent){
        String selectedText = getEditor(anActionEvent).getSelectionModel().getSelectedText();
        return selectedText;
    }

    public static PsiFile getFirstPsiFileByFileName(AnActionEvent anActionEvent,String fileName){
        Project project=getProject(anActionEvent);
        PsiFile[] foundFiles = FilenameIndex.getFilesByName(project, fileName, GlobalSearchScope.allScope(project));
        if (foundFiles.length <= 0) {
            return null;
        }
        return foundFiles[0];
    }
}
