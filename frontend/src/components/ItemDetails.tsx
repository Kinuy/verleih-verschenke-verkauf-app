import "./ItemDetails.css"
import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";
import {Item} from "../models/Item.ts";

const initVal:Item = {
    "id":"",
    "name":"",
    "img":"",
    "description":"",
    "category":"TOOL",
    "status":"TO_LEND"
}

export default function ItemDetails() {

    const {id} = useParams<{id:string}>();
    const [item,setItem] = useState<Item>(initVal);

    function fetchItemById(){

        axios.get(`/api/item/${id}`)
            .then((response)=>{
                setItem(response.data)
            })
    }

    useEffect(() => {
        fetchItemById();
        console.log("test")
    }, [id]);

    return (
        <div className="item-detail-container">
            <p>{item.name}</p>
        </div>
    );
};