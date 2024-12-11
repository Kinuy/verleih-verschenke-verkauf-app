import "./ItemDetails.css"
import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";
import {Item} from "../models/Item.ts";
import MapCard from "./MapCard.tsx";

const initVal:Item = {
    "id":"",
    "name":"",
    "img":"",
    "description":"",
    "category":"TOOL",
    "status":"TO_LEND",
    "geocode":[0,0],
    "owner":""
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
            <div className="item-detail-card-container">
                <img src={item.img} alt="no image found"/>
                <p className="item-description-container">{item.description}</p>
            </div>
            <div className="item-detail-map-container">
            <MapCard item={item}/>
            </div>
        </div>

    );
};