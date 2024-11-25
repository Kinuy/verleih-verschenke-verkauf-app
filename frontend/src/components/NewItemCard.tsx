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
        axios.post("/api/item", itemData).then(
            (response)=>{
                setItem(response.data)
            }
        )
        console.log({itemName,itemImg,itemDescription,itemCategory,itemStatus})
    }

    useEffect(() => {
        if (item) {
            console.log("Item state updated:", item);
        }
    }, [item]);

    return (
        <div>
            <form className="newItemForm" onSubmit={addItem}>
                <h2>Add Item</h2>
                <label>Item name:
                    <input
                        type="text"
                        value={itemName}
                        placeholder="name"
                        onChange={(e) => setItemName(e.target.value)}
                    />
                </label>
                <label>Image:
                    <input
                        type="text"
                        value={itemImg}
                        placeholder="image"
                        onChange={(e) => setItemImg(e.target.value)}
                    />
                </label>
                <label>Description:
                    <input
                        type="text"
                        value={itemDescription}
                        placeholder="description"
                        onChange={(e) => setItemDescription(e.target.value)}
                    />
                </label>
                <label>Category:
                    <input
                        type="text"
                        value={itemCategory}
                        placeholder="TOOL"
                        onChange={(e) => setItemCategory(e.target.value)}
                    />
                </label>
                <label>Status:
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