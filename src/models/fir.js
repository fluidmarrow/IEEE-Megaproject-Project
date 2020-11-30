const mongoose = require('mongoose')
const firSchema = new mongoose.Schema({
    name:{type:String,required:true,trim:true},
    father:{type:String,required:true,trim:true},
    location:{type:String,required:true,trim:true},
    district:{type:String,required:true,trim:true},
    category:{type:String,required:true,trim:true},
    description:{type:String,required:true,trim:true},
    number:{type:String,required:true},
    status:{type:String,required:true}
},{timestamps:true})
const Fir = mongoose.model('Fir',firSchema)
module.exports=Fir
