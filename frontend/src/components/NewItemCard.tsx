import {Item} from "../models/Item.ts";
import {FormEvent, useEffect, useState} from "react";
import axios from "axios";
import "./NewItemCard.css";
import {ItemCategory} from "../models/ItemCategory.ts";
import {ItemStatus} from "../models/ItemStatus.ts";
import {ItemDto} from "../models/ItemDto.ts";

export default function NewItemCard() {

    const itemData: Item = {
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
        if(!itemCategory || !itemStatus){
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
            .then((response)=>{setItem(response.data)})
            .catch(error => {console.error("Error adding item:", error);
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
                        <input
                            type="text"
                            value={itemCategory}
                            placeholder="TOOL"
                            onChange={(e) => setItemCategory(e.target.value)}
                        />
                </label>
                <label>
                    <p>Status:</p>
                        <input
                            type="text"
                            value={itemStatus}
                            placeholder="TO_SELL"
                            onChange={(e) => setItemStatus(e.target.value)}
                        />
                </label>

                <button>add</button>
            </form>
        </div>
    );
};