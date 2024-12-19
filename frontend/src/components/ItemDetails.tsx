import "./ItemDetails.css"
import {useParams} from "react-router-dom";
import {useEffect, useState} from "react";
import {Item} from "../models/Item.ts";
import MapCard from "./MapCard.tsx";
import axiosAccess from "../utility/access.ts";

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

        axiosAccess.get(`/api/item/${id}`)
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
                <img className="item-detail-img-container" src={item.img} alt="no image found"/>
                <div className="item-detail-content-container">
                    <p className="label-container">Name: {item.name}</p>
                    <p className="label-container">Owner: {item.owner}</p>
                    <p className="label-container">Category: {item.category}</p>
                    <p className="label-container">Status: {item.status}</p>
                </div>
                <p className="item-description-container">Description:<br/> {item.description}</p>
            </div>
            <div className="item-detail-map-container">
                <MapCard item={item}/>
            </div>
        </div>

    );
};