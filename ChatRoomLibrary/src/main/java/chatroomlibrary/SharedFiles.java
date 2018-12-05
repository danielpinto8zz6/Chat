package chatroomlibrary;

import java.io.File;

import javax.swing.tree.DefaultMutableTreeNode;

import chatroomlibrary.FileInfo;

public class SharedFiles extends DefaultMutableTreeNode {
    private static final long serialVersionUID = 1L;

    public SharedFiles(String title, File rootFolder) {
        super(title);

        DefaultMutableTreeNode root = new DefaultMutableTreeNode(new FileInfo(rootFolder));
        add(root);

        CreateChildNodes ccn = new CreateChildNodes(rootFolder, root);
    }

    public class CreateChildNodes {

        public CreateChildNodes(File fileRoot, DefaultMutableTreeNode root) {
            createChildren(fileRoot, root);
        }

        private void createChildren(File fileRoot, DefaultMutableTreeNode node) {
            File[] files = fileRoot.listFiles();
            if (files == null)
                return;

            for (File file : files) {
                DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(new FileInfo(file));
                node.add(childNode);
                if (file.isDirectory()) {
                    createChildren(file, childNode);
                }
            }
        }
    }
}