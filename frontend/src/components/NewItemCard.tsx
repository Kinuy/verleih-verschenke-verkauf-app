import {Item} from "../models/Item.ts";
import {FormEvent, useEffect, useState} from "react";
import axios from "axios";
import "./NewItemCard.css";
import {ItemCategory} from "../models/ItemCategory.ts";
import {ItemStatus} from "../models/ItemStatus.ts";
import {ItemDto} from "../models/ItemDto.ts";

export default function NewItemCard() {

    const itemData: Item = {
        id: "",
        name: "",
        img: "",
        description: "",
        category: "TOOL",
        status: "TO_SELL"
    };

    const [item, setItem] = useState<Item>(itemData)

    const [itemName, setItemName] = useState("");
    const [itemImg, setItemImg] = useState("");
    const [itemDescription, setItemDescription] = useState("");
    const [itemCategory, setItemCategory] = useState<ItemCategory>();
    const [itemStatus, setItemStatus] = useState<ItemStatus>();


    function addItem(event: FormEvent<HTMLFormElement>) {
        event.preventDefault()
        if (!itemCategory || !itemStatus) {
            alert("Please check your input: category or status!");
            return;
        }

        const savedItem: ItemDto = {
            name: itemName || 'Default Name',
            img: itemImg || 'default/url',
            description: itemDescription || 'Default description',
            category: itemCategory || 'TOOL',
            status: itemStatus || 'TO_SELL'
        };

        axios.post("/api/item", savedItem)
            .then((response) => {
                setItem(response.data)
            })
            .catch(error => {
                console.error("Error adding item:", error);
            });
    }


    return (
        <div>
            <form className="newItemForm" onSubmit={addItem}>
                <h2>Add Item</h2>
                <label>
                    <p>Item name:</p>
                    <input
                        type="text"
                        value={itemName}
                        placeholder="name"
                        onChange={(e) => setItemName(e.target.value)}
                    />
                </label>
                <label>
                    <p>Image:</p>
                    <input
                        type="text"
                        value={itemImg}
                        placeholder="image"
                        onChange={(e) => setItemImg(e.target.value)}
                    />
                </label>
                <label>
                    <p>Description:</p>
                    <input
                        type="text"
                        value={itemDescription}
                        placeholder="description"
                        onChange={(e) => setItemDescription(e.target.value)}
                    />
                </label>
                <label>
                    <p>Category:</p>
                    <select name="category" id="category-select" onChange={(e) => setItemCategory(e.target.value)}>
                        <option value="">--Choose category--</option>
                        <option value={itemCategory}>TOOL</option>
                        <option value={itemCategory}>MATERIAL</option>
                    </select>
                </label>
                <label>
                    <p>Status:</p>
                    <select name="status" id="status-select" onChange={(e) => setItemStatus(e.target.value)}>
                        <option value="">--Choose status--</option>
                        <option value={itemStatus}>TO_LEND</option>
                        <option value={itemStatus}>TO_GIVE_AWAY</option>
                        <option value={itemStatus}>TO_SELL</option>
                    </select>
                </label>
                <button>add</button>
            </form>
        </div>
    );
};