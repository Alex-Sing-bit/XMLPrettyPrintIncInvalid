package ru.vsu.cs.baklanova;

import java.util.ArrayList;
import java.util.Objects;

public class XMLTree<T extends Comparable<? super T>> {
    protected static class TreeNode {
        public ArrayList<String> tags;
        public String  value;
        public ArrayList<TreeNode> children;

        public boolean closed;

        public TreeNode(ArrayList<String> tags, String value, ArrayList<TreeNode> children, boolean closed) {
            this.tags = tags;
            this.value = value;
            this.children = children;
            this.closed = closed;
        }

        public TreeNode(String value, boolean closed) {
            this(null, value, null, closed);
        }

        public void stringToTags(String str) {
            ArrayList<String> arr = new ArrayList<>();
            while (str.contains(" ")) {
                int ind = str.indexOf(" ");
                String s = str.substring(0, ind + 1).trim();
                if (!s.equals("")) {
                    arr.add(s.trim());
                }
                str = str.substring(ind).trim();
            }

            if (!str.equals("")) {
                arr.add(str);
            }

            int size = arr.size();
            for (int i = 1; i < size; i++) {
                String s = "";
                if (arr.get(i).equals("=") && (i == size - 1 || arr.get(i + 1).indexOf("\"") == 0)) {
                    s = arr.get(i - 1) + "=";
                    if (i != size - 1) {
                        s += arr.get(i + 1);
                        arr.remove(i + 1);
                        size--;
                    }
                    arr.add(i - 1, s);
                    arr.remove(i);
                    arr.remove(i);
                    size--;
                    i--;
                }
            }

            this.tags = arr;
        }
        public String tagsToString() {
            String s = "";
            for (int i = 0; i < tags.size(); i++) {
                s += tags.get(i).trim();
                if (i != tags.size() - 1) {
                    s += " ";
                }
            }
            return s;
        }
        public void addChildren(TreeNode child) {
            if (children == null) {
                children = new ArrayList<>();
            }
            children.add(child);
        }

        public void addChildren(ArrayList<TreeNode> child) {
            children = child;
        }
    }

    protected TreeNode root = new TreeNode("", false);

    public TreeNode getRoot() {
        return root;
    }

    public void clear() {
        root = null;
    }

    public void fromStringXML(String str) {
        while (!Objects.equals(str, "")){
            str = str.trim();
            int st = str.indexOf('<');
            if (str.equals("") || st == -1) {
                return;
            }
            int fin = str.indexOf('>') + 1;
            if (fin == 0) {
                fin = str.charAt(str.length() - 1);
            }

            String subStr = str.substring(st + 1, fin - 1);

            str = str.substring(fin);
            String value = "";
            st = str.indexOf('<');
            if (st > 0) {
                value += str.substring(0, st).trim();
                str = str.substring(st).trim();
            }

            //ArrayList<String> sbs = new ArrayList<>(Arrays.asList(subStr.split(" ")));

            boolean isComment = ((subStr.startsWith("!-")));
            boolean isXMLVersion = (subStr.charAt(0) == '?' && subStr.charAt(subStr.length() - 1) == '?');
            boolean isClosedTag = subStr.charAt(0) == '/';
            boolean closed = isClosedTag || subStr.charAt(subStr.length() - 1) == '/' || isXMLVersion || isComment;

            TreeNode thisNode = new TreeNode(null, value, null, closed);//" " - не учтено содержимое двойного тэга, закрываемость
            thisNode.stringToTags(subStr);
            root.addChildren(thisNode);
            if (thisNode.closed) {
                fromStringXML(str);
                str = "";
            } else {
                str = fromStringXML(str, thisNode);
            }
        }
    }

    private String fromStringXML(String str, TreeNode node) {
        while (!node.closed) {
            str = str.trim();
            int st = str.indexOf('<');
            if (str.equals("") || st == -1) {
                return str;
            }
            int fin = str.indexOf('>') + 1;
            if (fin == 0) {
                fin = str.charAt(str.length() - 1);
            }

            String subStr = str.substring(st + 1, fin - 1).trim();

            str = str.substring(fin).trim();
            String value = "";
            st = str.indexOf('<');
            if (st > 0) {
                value += str.substring(0, st);
                str = str.substring(st);
            }

            boolean isComment = subStr.startsWith("!-");
            boolean isClosedTag = subStr.charAt(0) == '/';
            boolean closed = isClosedTag || subStr.charAt(subStr.length() - 1) == '/' || isComment;

            TreeNode thisNode = new TreeNode(null, value, null, closed);
            thisNode.stringToTags(subStr);
            if (Objects.equals(thisNode.tags.get(0), "/" + node.tags.get(0))) {
                node.closed = true;
                str = "<" + subStr + ">" + str;
            } else {
                if (isClosedTag) {
                    System.out.print("Предупреждение - тэг <" + node.tagsToString() + "> не был закрыт до появления закрывающего тэга <" + thisNode.tagsToString());
                    System.out.println(">. Код далее отредактирован с учетом мнения, что был потерян открывающий тэг для <" + thisNode.tagsToString() + ">");
                    node.addChildren(thisNode);
                } else {
                    node.addChildren(thisNode);
                    str = fromStringXML(str, thisNode);
                }
            }
        }
        return str;
    }

    public ArrayList<String> xmlTreeToStrings() {
        ArrayList<String> arr = new ArrayList<>();
        xmlTreeToStrings(arr, root, -1);

        return arr;
    }

    private void xmlTreeToStrings(ArrayList<String> arr, TreeNode node, int level) {
        final String space = "\t";
        if (level >= 0) {
            arr.add((space.repeat(level)) + "<" + node.tagsToString() + ">" + "\n");
            if (!node.value.trim().equals("")) {
                arr.add((space.repeat(level + 1)) + node.value + "\n");
            }
        }
        if (node.children != null) {
            for (TreeNode n : node.children) {
                xmlTreeToStrings(arr, n, level + 1);
            }
        }
    }
}

//Удаление значения при самозакрывающихся/закрывающих тэгах
//Смена цвета??
//Текст до первого тега игнорируется
//Оконный интерфейс

//<a> <d> </a> - назначить закрытие двух тэгов при нахождении закрывающего тэга / нет закрывающего тэга

//<d> </a> </d> - назначить закрытие двух тэгов при нахождении закрывающего тэга / нет открывающего тэга
//Вариант приоритетнее, дает решение. В целом ситуация - противоречие
//Лишний тэг должен закрывать сам себя(?)