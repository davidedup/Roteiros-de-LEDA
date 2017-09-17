package adt.rbtree;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;
import adt.bt.BTNode;
import adt.bt.Util;
import adt.rbtree.RBNode.Colour;

public class RBTreeImpl<T extends Comparable<T>> extends BSTImpl<T> implements RBTree<T> {

   public RBTreeImpl() {
      this.root = new RBNode<T>();
   }

   protected int blackHeight() {
      return this.blackHeight(this.root);
   }

   protected int blackHeight(BTNode<T> node) {
      int height = 1;
      if (node == null || node.isEmpty()) {
         height = 1;
      }
      if (this.isBlack((RBNode<T>) node)) {
         if (this.blackHeight(node.getRight()) > this.blackHeight(node.getLeft())) {
            height = 1 + this.blackHeight(node.getRight());
         } else {
            height = 1 + this.blackHeight(node.getLeft());
         }
      } else {
         if (this.blackHeight(node.getRight()) > this.blackHeight(node.getLeft())) {
            height = this.blackHeight(node.getRight());
         } else {
            height = this.blackHeight(node.getLeft());
         }
      }
      return height;
   }

   /**
    * Verifica se o node eh preto
    * 
    * @param node
    * @return - boolean true se o no eh preto e false se eh vermelho
    */
   private boolean isBlack(RBNode<T> node) {
      if (node != null) {
         return node.getColour() == Colour.BLACK;
      }
      return true;
   }

   protected boolean verifyProperties() {
      boolean resp = verifyNodesColour() && verifyNILNodeColour() && verifyRootColour() && verifyChildrenOfRedNodes()
            && verifyBlackHeight();

      return resp;
   }

   /**
    * The colour of each node of a RB tree is black or red. This is guaranteed
    * by the type Colour.
    */
   private boolean verifyNodesColour() {
      return true; // already implemented
   }

   /**
    * The colour of the root must be black.
    */
   private boolean verifyRootColour() {
      return ((RBNode<T>) root).getColour() == Colour.BLACK; // already
      // implemented
   }

   /**
    * This is guaranteed by the constructor.
    */
   private boolean verifyNILNodeColour() {
      return true; // already implemented
   }

   /**
    * Verifies the property for all RED nodes: the children of a red node must
    * be BLACK.
    */
   private boolean verifyChildrenOfRedNodes() {
      return verifyChildrenOfRedNodes((RBNode<T>) this.getRoot());
   }

   private boolean verifyChildrenOfRedNodes(RBNode<T> node) {
      boolean resp = true;
      if (!node.isEmpty()) {
         if (!this.isBlack(node)) {
            if (!isBlack((RBNode<T>) node.getLeft()) || !isBlack((RBNode<T>) node.getRight())) {
               resp = false;
            }
         }
         return this.verifyChildrenOfRedNodes((RBNode<T>) node.getLeft())
               && this.verifyChildrenOfRedNodes((RBNode<T>) node.getRight());
      }
      return resp;
   }

   /**
    * Verifies the black-height property from the root. The method blackHeight
    * returns an exception if the black heights are different.
    */
   private boolean verifyBlackHeight() {
      boolean resp = false;
      int leftHeight = this.verifyBlackHeight((RBNode<T>) this.root.getLeft(), 0);
      int rigthHeight = this.verifyBlackHeight((RBNode<T>) this.root.getRight(), 0);

      if (leftHeight == rigthHeight) {
         resp = true;
      }
      return resp;
   }

   private int verifyBlackHeight(RBNode<T> node, int height) {
      int resp;
      if (node == null || node.isEmpty())
         resp = height++;
      else {
         if (this.isBlack(node))
            height++;
         resp = Math.max(this.verifyBlackHeight((RBNode<T>) node.getLeft(), height),
               this.verifyBlackHeight((RBNode<T>) node.getRight(), height));
      }
      return resp;
   }

   @Override
   public void insert(T value) {
      if (value != null) {
         this.insertBR((RBNode<T>) this.root, value);
      }
   }

   /**
    * Insere o node na arvore, como era feito na BST.Além disto setta a cor do
    * node para red e chama o caso 1 do fixup apos a inserção
    * 
    * @param node
    * @param value
    *            - valor a ser inserido
    */
   private void insertBR(RBNode<T> node, T value) {
      if (node.isEmpty()) {
         node.setData(value);
         node.setLeft(new RBNode<T>());
         node.getLeft().setParent(node);
         node.setRight(new RBNode<T>());
         node.getRight().setParent(node);
         node.setColour(Colour.RED);
         fixUpCase1(node);
      } else if (value.compareTo(node.getData()) < 0) {
         this.insertBR((RBNode<T>) node.getLeft(), value);
      } else if (value.compareTo(node.getData()) > 0) {
         this.insertBR((RBNode<T>) node.getRight(), value);
      }
   }

   @Override
   public RBNode<T>[] rbPreOrder() {
      RBNode<T>[] array = (RBNode<T>[]) new RBNode[this.size()];
      this.rbPreOrder(array, 0, (RBNode<T>) this.getRoot());
      return array;
   }

   private int rbPreOrder(RBNode<T>[] array, int index, RBNode<T> node) {
      if (!node.isEmpty()) {
         array[index++] = node;
         index = this.rbPreOrder(array, index, (RBNode<T>) node.getLeft());
         index = this.rbPreOrder(array, index, (RBNode<T>) node.getRight());
      }
      return index;
   }

   // FIXUP methods
   protected void fixUpCase1(RBNode<T> node) {
      if (this.root.equals(node)) {
         node.setColour(Colour.BLACK);
      } else {
         this.fixUpCase2(node);
      }
   }

   protected void fixUpCase2(RBNode<T> node) {
      if (!this.isBlack((RBNode<T>) node.getParent())) {// se o pai do no adicionado for vermelho
         this.fixUpCase3(node);
      }
   }

   protected void fixUpCase3(RBNode<T> node) {
      RBNode<T> parent = (RBNode<T>) node.getParent();
      RBNode<T> grandParent = (RBNode<T>) parent.getParent();
      RBNode<T> uncle = this.getUncle(node, parent, grandParent);

      if (!isBlack(uncle)) {
         parent.setColour(Colour.BLACK);
         uncle.setColour(Colour.BLACK);
         grandParent.setColour(Colour.RED);
         fixUpCase1(grandParent);
      } else {
         fixUpCase4(node);
      }
   }

   /**
    * Retorna o tio de um node dado o seu avo e pai;
    * 
    * @param node
    * @param parent
    * @param grandParent
    * @return
    */
   private RBNode<T> getUncle(RBNode<T> node, RBNode<T> parent, RBNode<T> grandParent) {
      RBNode<T> uncle = null;
      if (isLeftChild(parent)) {
         uncle = (RBNode<T>) grandParent.getRight();
      } else {
         uncle = (RBNode<T>) grandParent.getLeft();
      }
      return uncle;

   }

   protected void fixUpCase4(RBNode<T> node) {
      RBNode<T> nodeAux = node;

      if (!this.isLeftChild(node) && this.isLeftChild((BSTNode<T>) node.getParent())) {
         Util.leftRotation((BSTNode<T>) node.getParent());
         nodeAux = (RBNode<T>) node.getLeft();
      } else if (this.isLeftChild(node) && !this.isLeftChild((BSTNode<T>) node.getParent())) {
         Util.rightRotation((BSTNode<T>) node.getParent());
         nodeAux = (RBNode<T>) node.getRight();
      }

      this.fixUpCase5(nodeAux);
   }

   protected void fixUpCase5(RBNode<T> node) {
      RBNode<T> parent = (RBNode<T>) node.getParent();
      RBNode<T> grandParent = (RBNode<T>) parent.getParent();

      parent.setColour(Colour.BLACK);
      grandParent.setColour(Colour.RED);

      if (this.isLeftChild(node))
         Util.rightRotation(grandParent);
      else
         Util.leftRotation(grandParent);

   }
}