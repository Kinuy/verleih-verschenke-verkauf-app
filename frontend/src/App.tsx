//import './App.css'

import axios from "axios";
import {Item} from "./models/Item.ts";
import "./models/ItemCategory.ts";
import {FormEvent, useState} from "react";



export default function App() {

    const itemData: Item = {
        name: "Hammer",
        img: "url/hammer",
        description: "Hammer aus meinem Keller",
        category: "TOOL",
        status: "TO_SELL"
    };

    const [item, setItem] = useState<Item>(itemData)



    function addItem(event: FormEvent<HTMLFormElement>) {
        event.preventDefault()
        axios.post("/api/item", itemData).then(
            (response)=>{
                setItem(response.data)
                console.log(item)
            }
        )
    }

    return (
        <div>
            <h1>Landing Page</h1>
            <form onSubmit={addItem}>
                <button>add</button>
            </form>
        </div>
    )
}
