const express = require('express')
const auth = require('../middleware/auth.js')
const router = new express.Router()
const Fir = require('../models/fir')
router.post('/firs',async(req,res)=>{
    try{
        const fir = await new Fir(req.body)
        await fir.save()
        res.status(201).send(fir)
    }
    catch(error){res.status(400).send(error)
    console.log(error)}
})
router.get('/firs',auth,async(req,res)=>{
    const district=req.user.district
    try{
       const fir = await Fir.find({district})
       res.send(fir)
    }
    catch(error){res.status(500).send(error)}
})
router.patch('/firs/:id',auth,async (req,res)=>{
    const updates = Object.keys(req.body)
    const validupdates = ['status']
    const isvalidupdates = updates.every((update)=>validupdates.includes(update))
    if(!isvalidupdates){
        return res.status(400).send('error:'+ 'Invalid Updates')
    }
    try{
        const fir = await Fir.findById({_id:req.params.id})
        if(!fir){
            return res.status(404).send()
        }
        updates.forEach((update)=>fir[update]=req.body[update])
        await fir.save()
        res.send(fir)
    }catch(e){res.status(400).send(e)}
})
module.exports = router