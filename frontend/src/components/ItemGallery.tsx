import {useEffect, useState} from "react";
import {Item} from "../models/Item.ts";
import axios from "axios";
import ItemCard from "./ItemCard.tsx";
import "./ItemGallery.css";

export default function ItemGallery() {
    const [items, setItems] = useState<Item[]>()

    function fetchAllItems(){
        axios.get("/api/item")
            .then((response)=>setItems(response.data))
            .catch(error => {console.error("Error fetching item list:", error);
        });
    }

    useEffect(() => {
        fetchAllItems()
    }, []);

    return (
        <div className= "ItemGallery-container">
            <h2>My Items</h2>
            {
                items?.map((element)=>{return <ItemCard key={element.id} item={element}/>})
            }
        </div>
    );
};