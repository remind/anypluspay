package com.anypluspay.payment.domain.flux.chain;

import com.anypluspay.payment.domain.PaymentConstants;
import com.anypluspay.payment.domain.flux.FluxProcess;
import com.anypluspay.payment.domain.flux.FluxProcessStatus;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 交换指令链
 * @author wxj
 * 2025/2/7
 */
@Getter
public class FluxChain {

    /**
     * 头结点
     */
    private FluxProcessNode head;

    /**
     * 尾结点
     */
    private FluxProcessNode tail;

    // 构造函数
    public FluxChain() {
        this.head = null;
        this.tail = null;
    }

    // 在链表尾部插入节点
    public void append(FluxProcess data) {
        FluxProcessNode newNode = new FluxProcessNode(data);
        if (head == null) { // 如果链表为空
            head = newNode;
            tail = newNode;
            newNode.getFluxProcess().setPreProcessId(PaymentConstants.DEFAULT_NULL_VALUE);
        } else {
            tail.setNext(newNode); // 当前尾节点的后继指向新节点
            newNode.setPrev(tail); // 新节点的前驱指向当前尾节点
            tail = newNode;      // 更新尾节点为新节点
        }
    }

    // 查找节点
    public FluxProcessNode find(String instructId) {
        FluxProcessNode current = head;
        while (current != null) {
            if (current.getFluxProcess().getFluxProcessId().equals(instructId)) {
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
        FluxProcessNode current = find(instructId);
        List<String> deleteIds = new ArrayList<>();
        if (current != null) {
            FluxProcessNode next = current.getNext();
            while (next != null) {
                deleteIds.add(next.getFluxProcess().getFluxProcessId());
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
    public FluxProcess insertAfterNode(FluxProcess targetData, List<FluxProcess> newInstructions) {
        FluxProcessNode targetNode = find(targetData.getFluxProcessId());
        if (targetNode == null) {
            return null;
        }
        FluxProcess oldNext = null;
        if (targetNode.getNext() != null) {
            oldNext = targetNode.getNext().getFluxProcess();
        }
        FluxProcessNode current = targetNode;
        for (FluxProcess newInstruction : newInstructions) {
            FluxProcessNode newNode = new FluxProcessNode(newInstruction);
            FluxProcessNode nextNode = current.getNext();

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
    public List<FluxProcess> toList() {
        List<FluxProcess> list = new ArrayList<>();
        FluxProcessNode current = head;
        while (current != null) {
            list.add(current.getFluxProcess());
            current = current.getNext();
        }
        return list;
    }

    /**
     * 获取可执行的的指令
     * @return
     */
    public FluxProcess getExecuteFluxInstruct() {
        FluxProcessNode current = head;
        while (current != null) {
            if (current.getFluxProcess().getStatus() == FluxProcessStatus.INIT) {
                return current.getFluxProcess();
            }
            current = current.getNext();
        }
        return null;
    }

}
