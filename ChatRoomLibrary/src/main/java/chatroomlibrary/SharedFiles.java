package chatroomlibrary;

import java.io.File;

import javax.swing.tree.DefaultMutableTreeNode;

import chatroomlibrary.FileInfo;

public class SharedFiles extends DefaultMutableTreeNode {
    private static final long serialVersionUID = 1L;
    private String owner;

    public SharedFiles(File rootFolder, String owner) {
        super(rootFolder.getName());
        this.owner = owner;

        new CreateChildNodes(rootFolder, this);
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
                FileInfo fileInfo = new FileInfo(file);
                fileInfo.setOwner(owner);
                DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(fileInfo);
                node.add(childNode);
                if (file.isDirectory()) {
                    createChildren(file, childNode);
                }
            }
        }
    }
}