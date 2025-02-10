package com.anypluspay.payment.domain.flux.chain;

import com.anypluspay.payment.domain.PaymentConstants;
import com.anypluspay.payment.domain.flux.FluxInstruction;
import com.anypluspay.payment.domain.flux.InstructStatus;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 指令链
 * @author wxj
 * 2025/2/7
 */
@Getter
public class InstructChain {

    /**
     * 头结点
     */
    private InstructionNode head;

    /**
     * 尾结点
     */
    private InstructionNode tail;

    // 构造函数
    public InstructChain() {
        this.head = null;
        this.tail = null;
    }

    // 在链表尾部插入节点
    public void append(FluxInstruction data) {
        InstructionNode newNode = new InstructionNode(data);
        if (head == null) { // 如果链表为空
            head = newNode;
            tail = newNode;
            newNode.getFluxInstruction().setPreInstructionId(PaymentConstants.DEFAULT_NULL_VALUE);
        } else {
            tail.setNext(newNode); // 当前尾节点的后继指向新节点
            newNode.setPrev(tail); // 新节点的前驱指向当前尾节点
            tail = newNode;      // 更新尾节点为新节点
        }
    }

    // 查找节点
    public InstructionNode find(String instructId) {
        InstructionNode current = head;
        while (current != null) {
            if (current.getFluxInstruction().getInstructionId().equals(instructId)) {
                return current;
            }
            current = current.getNext();
        }
        return null;
    }

    /**
     * 删除指定节点后的所有节点，并返回删除节点的ID
     *
     * @param instructId
     * @return
     */
    public List<String> deleteAfterNode(String instructId) {
        InstructionNode current = find(instructId);
        List<String> deleteIds = new ArrayList<>();
        if (current != null) {
            InstructionNode next = current.getNext();
            while (next != null) {
                deleteIds.add(next.getFluxInstruction().getInstructionId());
                next = next.getNext();
            }
            tail = current;
            current.setNext(null);
        }
        return deleteIds;
    }

    /**
     * 在指定节点后面插入多个节点
     *
     * @param targetData
     * @param newInstructions
     */
    public FluxInstruction insertAfterNode(FluxInstruction targetData, List<FluxInstruction> newInstructions) {
        InstructionNode targetNode = find(targetData.getInstructionId());
        if (targetNode == null) {
            return null;
        }
        FluxInstruction oldNext = null;
        if (targetNode.getNext() != null) {
            oldNext = targetNode.getNext().getFluxInstruction();
        }
        InstructionNode current = targetNode;
        for (FluxInstruction newInstruction : newInstructions) {
            InstructionNode newNode = new InstructionNode(newInstruction);
            InstructionNode nextNode = current.getNext();

            newNode.setPrev(current);
            newNode.setNext(nextNode);

            if (nextNode != null) {
                nextNode.setPrev(newNode);
            } else {
                tail = newNode; // 如果当前节点是尾节点，更新尾节点为新节点
            }
            current.setNext(newNode);
            current = newNode;
        }
        return oldNext;
    }

    /**
     * 将链表转换为列表
     *
     * @return
     */
    public List<FluxInstruction> toList() {
        List<FluxInstruction> list = new ArrayList<>();
        InstructionNode current = head;
        while (current != null) {
            list.add(current.getFluxInstruction());
            current = current.getNext();
        }
        return list;
    }

    /**
     * 获取可执行的的指令
     * @return
     */
    public FluxInstruction getExecuteFluxInstruct() {
        InstructionNode current = head;
        while (current != null) {
            if (current.getFluxInstruction().getStatus() == InstructStatus.INIT) {
                return current.getFluxInstruction();
            }
            current = current.getNext();
        }
        return null;
    }

}
