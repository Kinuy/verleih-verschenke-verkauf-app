import {Item} from "../models/Item.ts";
import {FormEvent, useEffect, useState} from "react";
import axios from "axios";
import "./NewItemCard.css";

export default function NewItemCard() {

    const itemData: Item = {
        name: "item ame",
        img: "item img_url",
        description: "item description",
        category: "TOOL",
        status: "TO_SELL"
    };

    const [item, setItem] = useState<Item>(itemData)

    const [itemName, setItemName] = useState("");
    const [itemImg, setItemImg] = useState("");
    const [itemDescription, setItemDescription] = useState("");
    const [itemCategory, setItemCategory] = useState("");
    const [itemStatus, setItemStatus] = useState("");



    function addItem(event: FormEvent<HTMLFormElement>) {
        event.preventDefault()
        axios.post("/api/item", itemData)
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