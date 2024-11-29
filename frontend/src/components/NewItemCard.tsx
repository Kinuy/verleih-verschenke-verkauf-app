import {Item} from "../models/Item.ts";
import {FormEvent, useEffect, useState} from "react";
import axios from "axios";
import "./NewItemCard.css";
import {ItemCategory} from "../models/ItemCategory.ts";
import {ItemStatus} from "../models/ItemStatus.ts";
import {ItemDto} from "../models/ItemDto.ts";


type Props = {
    updateList: ()=>void;
}

export default function NewItemCard(props: Props) {

    const [itemName, setItemName] = useState("");
    const [itemImg, setItemImg] = useState("");
    const [itemDescription, setItemDescription] = useState("");
    const [itemCategory, setItemCategory] = useState<ItemCategory>();
    const [itemStatus, setItemStatus] = useState<ItemStatus>();
    const [deleteId, setDeleteId] = useState<string>("")
    const [items,setItems] = useState<Item[]>([])


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
            .then(() => {
                props.updateList();
            })
            .catch(error => {
                console.error("Error adding item:", error);
            });
    }

    function fetchAllItems(){
        axios.get("/api/item")
            .then((response)=>{
                setItems(response.data);
            })
            .catch(error => {console.error("Error fetching item list:", error);
            });
    }

    function deleteItem(){
        const checkDelete = window.confirm("Do you reeeeeeeeeeaaaaaaly want to delete this item?")
        if(checkDelete){
            axios.delete(`api/item/${deleteId}`)
                .then(props.updateList)
                .catch((error)=>{console.error(error)})
        }
    }

    useEffect(() => {
        fetchAllItems()
    }, [deleteId]);

    return (
        <div>
            <form className="newItemForm" onSubmit={addItem}>
                <h2>Modify Item</h2>
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
                    <select name="category" id="category-select"
                            onChange={(e) => setItemCategory(e.target.value as ItemCategory)}>
                        <option value="">--Choose category--</option>
                        <option value={itemCategory}>TOOL</option>
                        <option value={itemCategory}>MATERIAL</option>
                    </select>
                </label>
                <label>
                    <p>Status:</p>
                    <select name="status" id="status-select"
                            onChange={(e) => setItemStatus(e.target.value as ItemStatus)}>
                        <option value="">--Choose status--</option>
                        <option value={itemStatus}>TO_LEND</option>
                        <option value={itemStatus}>TO_GIVE_AWAY</option>
                        <option value={itemStatus}>TO_SELL</option>
                    </select>
                </label>
                <button>add</button>
            </form>

            <div className="delete-item-container">
                <select className="select-item-container" name="item" id="item-select" onChange={(e)=>setDeleteId(e.target.value)}>
                    <option value="">--Select item to delete--</option>
                    {
                        items.map((element) => {
                            return <option key={element.id} value={element.id}>{element.name}</option>
                        })
                    }
                </select>
                <button onClick={deleteItem}>delete</button>
            </div>

        </div>
    );
};