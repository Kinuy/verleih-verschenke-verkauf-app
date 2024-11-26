import {useEffect, useState} from "react";
import {Item} from "../models/Item.ts";
import axios from "axios";
import ItemCard from "./ItemCard.tsx";
import "./ItemGallery.css";
import ItemSearchBar from "./ItemSearchBar.tsx";

export default function ItemGallery() {
    const [items, setItems] = useState<Item[]>([])
    const [filteredItems, setFilteredItems] = useState<Item[]>([]);

    function fetchAllItems(){
        axios.get("/api/item")
            .then((response)=>{
                setItems(response.data);
                setFilteredItems(response.data);
            })
            .catch(error => {console.error("Error fetching item list:", error);
        });
    }

    const filterItems = (query: string) => {
        if (query.trim() === "") {
            setFilteredItems(items);
            console.log("ok")
        } else {
            const lowercasedQuery = query.toLowerCase();
            const filtered = items.filter((item) =>
                item.name.toLowerCase().startsWith(lowercasedQuery)
            );
            setFilteredItems(filtered);
            console.log("ja")
        }
    };

    useEffect(() => {
        fetchAllItems()
    }, []);

    return (
        <div>
            <h2>Stuff</h2>
            <ItemSearchBar onSearch={filterItems}/>
            <div className= "ItemGallery-container">
            {
                filteredItems.map((element)=>{
                    if (element.status === "TO_SELL") {
                        return (
                            <div key={element.id} className="itemCardStatusSell-container">
                                <ItemCard item={element} />
                            </div>
                        );
                    } else if (element.status === "TO_LEND") {
                        return (
                            <div key={element.id} className="itemCardStatusLend-container">
                                <ItemCard item={element} />
                            </div>
                        );
                    } else if (element.status === "TO_GIVE_AWAY") {
                        return (
                            <div key={element.id} className="itemCardStatusGive-container">
                                <ItemCard item={element} />
                            </div>
                        );
                    } else {
                        return null;
                    }})
            }
            </div>
        </div>
    );
};